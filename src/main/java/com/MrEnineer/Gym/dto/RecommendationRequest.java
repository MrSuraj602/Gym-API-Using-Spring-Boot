package com.MrEnineer.Gym.dto;

import lombok.Data;
import java.util.List;

@Data
public class RecommendationRequest {
    private String userId;
    private String activityId;
}
