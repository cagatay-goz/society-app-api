package com.metuncc.society_app_api.ReservationRequest;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class GetAllReservationRequestDTO{
    private Long id;
    private String societyName;
    private String eventName;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private String location;
    private String status;
    private String presidentEmail; // President's full name
}
