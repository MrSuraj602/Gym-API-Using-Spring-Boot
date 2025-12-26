package com.MrEnineer.Gym.Service;

import com.MrEnineer.Gym.Entity.User;
import com.MrEnineer.Gym.Repository.UserRepository;
import com.MrEnineer.Gym.dto.RegisterRequest;
import com.MrEnineer.Gym.dto.UserResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse register(RegisterRequest user) {
        User newUser = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getFirstName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        User saveUser = userRepository.save(newUser);

        UserResponse response = new UserResponse();

        response.setEmail(saveUser.getEmail());
        response.setPassword(saveUser.getPassword());
        response.setId(saveUser.getId());
        response.setFirstName(saveUser.getFirstName());
        response.setLastName(saveUser.getLastName());
        response.setCreatedAt(saveUser.getCreatedAt());
        response.setUpdatedAt(saveUser.getUpdatedAt());

        return response;
    }
}
