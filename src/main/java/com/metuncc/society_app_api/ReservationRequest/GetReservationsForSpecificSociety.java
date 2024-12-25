package com.metuncc.society_app_api.ReservationRequest;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class GetReservationsForSpecificSociety {
    private Long id;
    private String eventName;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private String location;
    private String status;
}
