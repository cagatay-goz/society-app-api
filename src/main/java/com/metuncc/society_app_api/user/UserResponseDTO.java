package com.metuncc.society_app_api.user;

import lombok.Data;

import java.util.Set;

@Data
public class UserResponseDTO {
    private Long id;
    private String email;
    private Set<String> roles;
}
