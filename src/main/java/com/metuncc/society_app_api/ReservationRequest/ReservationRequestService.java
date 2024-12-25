package com.metuncc.society_app_api.ReservationRequest;

import com.metuncc.society_app_api.user.User;
import com.metuncc.society_app_api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationRequestService {

    @Autowired
    private ReservationRequestRepository reservationRequestRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new reservation request
    public String createReservationRequest(CreateReservationRequest requestDto) {
        // Find the president by email
        User president = userRepository.findByEmail(requestDto.getPresidentEmail());
        if (president == null) {
            throw new RuntimeException("President not found with email: " + requestDto.getPresidentEmail());
        }

        // Check if the user is a president
        if (!president.getRoles().contains("ROLE_PRESIDENT")) {
            throw new RuntimeException("The user with email " + requestDto.getPresidentEmail() + " is not a president.");
        }
        String societyName;
        if (president.getPresidedSociety() != null && president.getPresidedSociety().getName() != null) {
            societyName = president.getPresidedSociety().getName();
        } else {
            throw new RuntimeException("The president does not preside over a valid society.");
        }


        // Find all admins
        List<User> admins = userRepository.findByRolesContaining("ROLE_ADMIN");
        if (admins.isEmpty()) {
            throw new RuntimeException("No admins found in the system.");
        }

        // Create and save the reservation request
        ReservationRequest request = new ReservationRequest();
        request.setPresident(president); // Set the president
        request.setEventName(requestDto.getEventName()); // Set the event name
        request.setEventDate(requestDto.getEventDate()); // Set the event date
        request.setEventTime(requestDto.getEventTime()); // Set the event time
        request.setLocation(requestDto.getLocation()); // Set the location
        request.setSocietyName(societyName);

        // Add all admins to the request
        admins.forEach(request.getAdmins()::add);

        reservationRequestRepository.save(request); // Save to the database

        return "Reservation request created successfully.";
    }

    // Retrieve all requests visible to a specific admin
    public List<GetAllReservationRequestDTO> getRequestsForAdmin(String adminEmail) {
        // Find the admin by email
        User admin = userRepository.findByEmail(adminEmail);
        if (admin == null) {
            throw new RuntimeException("Admin not found with email: " + adminEmail);
        }

        // Fetch requests for the admin
        List<ReservationRequest> requests = reservationRequestRepository.findByAdminsId(admin.getId());

        // Filter requests to only include those with "pending" status
        return requests.stream()
                .filter(request -> "pending".equals(request.getStatus())) // Only include pending requests
                .map(request -> {
                    GetAllReservationRequestDTO dto = new GetAllReservationRequestDTO();
                    dto.setId(request.getId());
                    dto.setSocietyName(request.getSocietyName());
                    dto.setEventName(request.getEventName());
                    dto.setEventDate(request.getEventDate());
                    dto.setEventTime(request.getEventTime());
                    dto.setLocation(request.getLocation());
                    dto.setStatus(request.getStatus());
                    dto.setPresidentEmail(request.getPresident().getEmail());
                    return dto;
                })
                .toList();
    }

    // Process reservation request (accept or reject)
    public String processReservationRequest(Long requestId, String action) {
        // Find the request by ID
        ReservationRequest request = reservationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Reservation request not found with ID: " + requestId));

        // Check if the request is still pending
        if (!"pending".equals(request.getStatus())) {
            throw new RuntimeException("Reservation request is already processed. Current status: " + request.getStatus());
        }

        // Process based on the action
        if ("accept".equalsIgnoreCase(action)) {
            request.setStatus("accepted");
        } else if ("reject".equalsIgnoreCase(action)) {
            request.setStatus("rejected");
        } else {
            throw new IllegalArgumentException("Invalid action. Use 'accept' or 'reject'.");
        }

        // Save the updated request
        reservationRequestRepository.save(request);

        return "Reservation request " + action + "ed successfully.";
    }

    // Retrieve all reservation requests created by a specific president
    public List<GetReservationsForSpecificSociety> getRequestsByPresident(String presidentEmail) {
        // Find the president by email
        User president = userRepository.findByEmail(presidentEmail);
        if (president == null) {
            throw new RuntimeException("President not found with email: " + presidentEmail);
        }

        // Check if the user is a president
        if (!president.getRoles().contains("ROLE_PRESIDENT")) {
            throw new RuntimeException("The user with email " + presidentEmail + " is not a president.");
        }

        // Fetch requests created by the president
        List<ReservationRequest> requests = reservationRequestRepository.findByPresidentId(president.getId());

        // Map ReservationRequest to GetReservationsForSpecificSociety
        return requests.stream()
                .map(request -> {
                    GetReservationsForSpecificSociety dto = new GetReservationsForSpecificSociety();
                    dto.setId(request.getId());
                    dto.setEventName(request.getEventName());
                    dto.setEventDate(request.getEventDate());
                    dto.setEventTime(request.getEventTime());
                    dto.setLocation(request.getLocation());
                    dto.setStatus(request.getStatus());
                    return dto;
                })
                .toList();
    }

}
