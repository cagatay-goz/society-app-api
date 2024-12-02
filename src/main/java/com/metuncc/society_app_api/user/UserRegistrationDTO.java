package com.metuncc.society_app_api.user;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String email;
    private String password;
}