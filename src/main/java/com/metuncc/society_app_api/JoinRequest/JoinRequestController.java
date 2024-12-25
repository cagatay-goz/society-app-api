package com.metuncc.society_app_api.JoinRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.metuncc.society_app_api.user.User;
import com.metuncc.society_app_api.user.UserRepository;
import com.metuncc.society_app_api.Society.SocietyRepository;

@RestController
@RequestMapping("/join-requests")
public class JoinRequestController {

    @Autowired
    private JoinRequestService joinRequestService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SocietyRepository societyRepository;

    @PostMapping
    public String createJoinRequest(@RequestParam String email, @RequestParam Long societyId) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "User not found with email: " + email;
        }
        return joinRequestService.createJoinRequest(user.getId(), societyId);
    }

    @PutMapping("/{requestId}")
    public String processJoinRequest(@PathVariable Long requestId, @RequestParam String action) {
        return joinRequestService.processJoinRequest(requestId, action);
    }
}
