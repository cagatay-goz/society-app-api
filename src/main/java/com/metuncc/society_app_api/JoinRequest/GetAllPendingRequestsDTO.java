package com.metuncc.society_app_api.JoinRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllPendingRequestsDTO {
    private Long id;
    private String userName;
    private String userSurname;
    private String userEmail;
    private String status;
    private LocalDateTime createdAt;

}
