package com.metuncc.society_app_api.JoinRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.metuncc.society_app_api.user.User;
import com.metuncc.society_app_api.user.UserRepository;
import com.metuncc.society_app_api.Society.Society;
import com.metuncc.society_app_api.Society.SocietyRepository;

@Service
public class JoinRequestService {

    @Autowired
    private JoinRequestRepository joinRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SocietyRepository societyRepository;

    public String createJoinRequest(Long userId, Long societyId) {
        // Check if there is already a pending request
        Optional<JoinRequest> existingRequest = joinRequestRepository.findByUserIdAndSocietyIdAndStatus(userId, societyId, "pending");

        if (existingRequest.isPresent()) {
            return "You already have a pending request for this society.";
        }

        // Retrieve User and Society from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
        Society society = societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found."));

        // Create a new join request
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setUser(user);
        joinRequest.setSociety(society);
        joinRequest.setStatus("pending");

        joinRequestRepository.save(joinRequest);

        return "Join request sent successfully.";
    }


    public String processJoinRequest(Long requestId, String action) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Join request not found."));

        // Ensure the request is still pending
        if (!"pending".equals(joinRequest.getStatus())) {
            return "Request is already processed.";
        }

        if ("accept".equalsIgnoreCase(action)) {
            // Accept the request and add the user to the society
            joinRequest.setStatus("accepted");

            Society society = joinRequest.getSociety();
            User user = joinRequest.getUser();

            society.getUsers().add(user);
            societyRepository.save(society);

            joinRequestRepository.save(joinRequest);
            return "Join request accepted and user added to society.";
        } else if ("reject".equalsIgnoreCase(action)) {
            // Reject the request
            joinRequest.setStatus("rejected");
            joinRequestRepository.save(joinRequest);
            return "Join request rejected.";
        } else {
            throw new IllegalArgumentException("Invalid action. Use 'accept' or 'reject'.");
        }
    }


    public List<GetAllPendingRequestsDTO> getJoinRequests(String email) {
        // Find the user by email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        // Check if the user presides over a society
        Society society = user.getPresidedSociety();
        if (society == null) {
            throw new RuntimeException("Society not found.");
        }

        // Fetch and map pending join requests to DTOs
        return joinRequestRepository.findBySocietyIdAndStatus(society.getId(), "pending")
                .stream()
                .map(request -> new GetAllPendingRequestsDTO(
                        request.getId(),
                        request.getUser().getName(),
                        request.getUser().getSurname(),
                        request.getUser().getEmail(),
                        request.getStatus(),
                        request.getCreatedAt()
                ))
                .toList();
    }
}
