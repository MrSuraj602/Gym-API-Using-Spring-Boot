package com.MrEnineer.Gym.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class RecommendationResponse {
    private String id;
    private String type;
    private String recomandation;
    private List<String> suggestion;
    private List<String> improvement;
    private List<String> safety;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
