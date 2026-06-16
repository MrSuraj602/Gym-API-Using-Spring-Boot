package com.MrEnineer.Gym.dto;

import com.MrEnineer.Gym.Entity.Gender;
import com.MrEnineer.Gym.Entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {
    private String firstName;

    private String lastName;

    private UserRole role;
    @Email(message = "Email is Invalid")
    @NotBlank(message = "Email is Required")
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    private int age;
    private int weight;
    private int height;
    private Gender gender;
}
