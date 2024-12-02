package com.metuncc.society_app_api.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserRegistrationDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.getRoles().add("ROLE_USER");
        return user;
    }

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());
        return dto;
    }
}
