package com.MrEnineer.Gym.Controller;

import com.MrEnineer.Gym.Entity.User;
import com.MrEnineer.Gym.Repository.UserRepository;
import com.MrEnineer.Gym.Service.UserService;
import com.MrEnineer.Gym.dto.LoginRequest;
import com.MrEnineer.Gym.dto.LoginResponse;
import com.MrEnineer.Gym.dto.RegisterRequest;
import com.MrEnineer.Gym.dto.UserResponse;
import com.MrEnineer.Gym.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest user){

        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        try{
            User user = userService.authenticate(loginRequest);
            String jwtToken = jwtUtils.generateAccessToken(user.getId(),user.getRole().name());
            return ResponseEntity.ok(new LoginResponse(jwtToken,userService.mapToResponse(user)));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build();
        }
    }
}
