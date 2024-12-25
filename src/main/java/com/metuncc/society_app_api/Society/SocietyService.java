package com.metuncc.society_app_api.Society;

import com.metuncc.society_app_api.user.User;
import com.metuncc.society_app_api.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocietyService {
    private final UserRepository userRepository;
    SocietyRepository societyRepository;

    public SocietyService(SocietyRepository societyRepository, UserRepository userRepository) {
        this.societyRepository = societyRepository;
        this.userRepository = userRepository;
    }

    public List<Society> getAllSocieties() {
        return societyRepository.findAll();
    }

    public Society getOneSociety(long societyId) {
        return societyRepository.findById(societyId).orElse(null);
    }

    public List<Society> getSocietiesByUserId(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        Long userId = user.getId();
        return societyRepository.findByUserId(userId);
    }

}
