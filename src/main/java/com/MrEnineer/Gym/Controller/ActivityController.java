package com.MrEnineer.Gym.Controller;

import com.MrEnineer.Gym.Entity.Activity;
import com.MrEnineer.Gym.Service.ActivityService;
import com.MrEnineer.Gym.dto.ActivityRequest;
import com.MrEnineer.Gym.dto.ActivityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping()
    public ResponseEntity<ActivityResponse> saveActivity(@RequestBody ActivityRequest activity){

        return ResponseEntity.ok(activityService.saveActivity(activity));
    }

    @GetMapping()
    public ResponseEntity<List<ActivityResponse>> getUserActivity(@RequestHeader(value = "X-User-ID") String userId){
        return ResponseEntity.ok(activityService.getUserActivity(userId));

    }
}
