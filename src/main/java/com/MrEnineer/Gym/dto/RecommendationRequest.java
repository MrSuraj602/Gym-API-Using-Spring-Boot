package com.MrEnineer.Gym.dto;

import lombok.Data;
import java.util.List;

@Data
public class RecommendationRequest {
    private String userId;
    private String activityId;
    private String type;
    private String recomandation;
    private List<String> suggestion;
    private List<String> improvement;
    private List<String> safety;
}
