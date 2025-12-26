package com.MrEnineer.Gym.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
