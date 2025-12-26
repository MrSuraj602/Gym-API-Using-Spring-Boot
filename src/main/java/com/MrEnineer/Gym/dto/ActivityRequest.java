package com.MrEnineer.Gym.dto;

import com.MrEnineer.Gym.Entity.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
@Data
public class ActivityRequest {

    private ActivityType type;

    private Map<String , Object> additionaMatrices;

    private Integer duration;
    private Integer caloriesBurn;

    private LocalDateTime startTime;

    private String userId;

}
