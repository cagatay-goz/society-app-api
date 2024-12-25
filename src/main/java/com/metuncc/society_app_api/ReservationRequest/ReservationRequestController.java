package com.metuncc.society_app_api.ReservationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation-requests")
public class ReservationRequestController {

    @Autowired
    private ReservationRequestService reservationRequestService;

    // Endpoint to create a new reservation request
    @PostMapping
    public String createReservationRequest(@RequestBody CreateReservationRequest requestDto) {
        return reservationRequestService.createReservationRequest(requestDto);
    }


    // Endpoint to retrieve all requests visible to a specific admin
    @GetMapping
    public List<GetAllReservationRequestDTO> getRequestsForAdmin(@RequestParam String adminEmail) {
        return reservationRequestService.getRequestsForAdmin(adminEmail);
    }

    // Process a reservation request (accept or reject)
    @PutMapping("/{requestId}")
    public String processReservationRequest(@PathVariable Long requestId, @RequestParam String action) {
        return reservationRequestService.processReservationRequest(requestId, action);
    }

    // Retrieve all requests created by a specific president
    @GetMapping("/by-president")
    public List<GetReservationsForSpecificSociety> getRequestsByPresident(@RequestParam String presidentEmail) {
        return reservationRequestService.getRequestsByPresident(presidentEmail);
    }


}
