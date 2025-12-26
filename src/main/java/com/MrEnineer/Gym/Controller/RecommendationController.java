package com.MrEnineer.Gym.Controller;

import com.MrEnineer.Gym.Service.RecommendationService;
import com.MrEnineer.Gym.dto.ActivityResponse;
import com.MrEnineer.Gym.dto.RecommendationRequest;
import com.MrEnineer.Gym.dto.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/generate")
    public ResponseEntity<RecommendationResponse> generate(@RequestBody RecommendationRequest recommendationRequest) {
        return ResponseEntity.ok(recommendationService.generate(recommendationRequest));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationResponse>> userRecommendation(@PathVariable String userId){
        return ResponseEntity.ok(recommendationService.userRecommendation(userId));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<RecommendationResponse>> activityRecommendation(@PathVariable String activityId){
        return ResponseEntity.ok(recommendationService.activityRecommendation(activityId));
    }

}
