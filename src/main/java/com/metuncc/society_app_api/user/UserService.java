package com.metuncc.society_app_api.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> signup(UserRegistrationDTO userRegistrationDTO)
    {
        try{
            if (userRepository.findByEmail(userRegistrationDTO.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists!");
            }

            User user = userMapper.toEntity(userRegistrationDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponseDTO(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during the signup process.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
