package com.MrEnineer.Gym.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @Email(message = "Email is Invalid")
    @NotBlank(message = "Email is Required")
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}
