package com.MrEnineer.Gym.Controller;

import com.MrEnineer.Gym.Service.UserService;
import com.MrEnineer.Gym.dto.RegisterRequest;
import com.MrEnineer.Gym.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest user){

        return ResponseEntity.ok(userService.register(user));
    }
}
