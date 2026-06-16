package com.MrEnineer.Gym.Service;

import com.MrEnineer.Gym.Entity.Gender;
import com.MrEnineer.Gym.Entity.User;
import com.MrEnineer.Gym.Entity.UserRole;
import com.MrEnineer.Gym.Repository.UserRepository;
import com.MrEnineer.Gym.dto.LoginRequest;
import com.MrEnineer.Gym.dto.RegisterRequest;
import com.MrEnineer.Gym.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest user) {
        UserRole role = user.getRole() != null ? user.getRole() : UserRole.USER;
        Gender gender = user.getGender() != null ? user.getGender() : Gender.MALE;
        User newUser = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(role)
                .password(passwordEncoder.encode(user.getPassword()))
                .age(user.getAge())
                .height(user.getHeight())
                .gender(gender)
                .weight(user.getWeight())
                .build();

        userRepository.save(newUser);
         return mapToResponse(newUser);
    }

    public UserResponse mapToResponse(User savedUser){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());

        return userResponse;
    }

    public User authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user == null) throw new RuntimeException("Invalid Credentials");
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword() )){
            throw new RuntimeException("Invalid Credentials");
        }
        return user;
    }
}
