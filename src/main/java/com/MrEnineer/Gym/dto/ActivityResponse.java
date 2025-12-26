package com.MrEnineer.Gym.dto;

import com.MrEnineer.Gym.Entity.ActivityType;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityResponse {
    private String id;

    private ActivityType type;

    private Map<String , Object> additionaMatrices;

    private Integer duration;
    private Integer caloriesBurn;

    private LocalDateTime startTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String userId;
}
