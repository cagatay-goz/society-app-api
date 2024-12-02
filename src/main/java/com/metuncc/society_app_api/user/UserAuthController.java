package com.metuncc.society_app_api.user;

import com.metuncc.society_app_api.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserAuthController(
            UserService userService,
            JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return userService.signup(userRegistrationDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        ResponseEntity<?> response = userService.login(userLoginDTO);

        if(response.getStatusCode().is2xxSuccessful()) {
            User authenticatedUser = (User) response.getBody();
            String token = jwtService.generateToken(authenticatedUser);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", token);

            assert authenticatedUser != null;
            responseData.put("email", authenticatedUser.getEmail());

            return ResponseEntity.ok(responseData);
        }

        return response;
    }
}
