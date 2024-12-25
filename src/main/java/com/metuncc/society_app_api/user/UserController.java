package com.metuncc.society_app_api.user;

import com.metuncc.society_app_api.Society.Society;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/my-society")
    public Long getPresidedSociety(@RequestParam String email) {
        return userService.getPresiedSociety(email);
    }
}
