package com.metuncc.society_app_api.Society;

import com.metuncc.society_app_api.user.User;
import com.metuncc.society_app_api.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocietyService {
    SocietyRepository societyRepository;
    UserRepository userRepository;

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

    public Society createSociety(String name, String description, String presidentEmail) {
        // Find the user by email
        User president = userRepository.findByEmail(presidentEmail);

        // Ensure the user is not already a president of another society
        if (president.getPresidedSociety() != null) {
            throw new RuntimeException("User with email " + presidentEmail + " is already a president of another society.");
        }

        // Add ROLE_PRESIDENT to the user's roles if not already present
        if (!president.getRoles().contains("ROLE_PRESIDENT")) {
            president.getRoles().add("ROLE_PRESIDENT");
            userRepository.save(president); // Save updated user
        }

        // Create and save the new society
        Society society = new Society();
        society.setName(name);
        society.setDescription(description);
        society.setPresident(president);

        return societyRepository.save(society);
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
