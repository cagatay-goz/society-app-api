package com.metuncc.society_app_api.ReservationRequest;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode
public class CreateReservationRequest {
    private String presidentEmail; // President's email
    private String eventName;      // Event name
    private LocalDate eventDate;   // Event date
    private LocalTime eventTime;   // Event time
    private String location;       // Event location
}
