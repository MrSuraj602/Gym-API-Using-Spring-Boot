package com.MrEnineer.Gym.Service;

import com.MrEnineer.Gym.Entity.Activity;
import com.MrEnineer.Gym.Entity.Recommendation;
import com.MrEnineer.Gym.Entity.User;
import com.MrEnineer.Gym.Repository.ActivityRepo;
import com.MrEnineer.Gym.Repository.RecommendationRepository;
import com.MrEnineer.Gym.Repository.UserRepository;
import com.MrEnineer.Gym.dto.ActivityResponse;
import com.MrEnineer.Gym.dto.RecommendationRequest;
import com.MrEnineer.Gym.dto.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final ActivityRepo activityRepo;

    public RecommendationResponse generate(RecommendationRequest recommendationRequest) {

        User user = userRepository.findById(recommendationRequest.getUserId())
                .orElseThrow(()->new RuntimeException("User Not Found "+recommendationRequest.getUserId()));

        Activity activity = activityRepo.findById(recommendationRequest.getActivityId())
                .orElseThrow(()->new RuntimeException("Activity Not Found "+ recommendationRequest.getActivityId()));

        Recommendation recommendation = Recommendation.builder()
                .type(recommendationRequest.getType())
                .user(user)
                .activity(activity)
                .suggestion(recommendationRequest.getSuggestion())
                .improvement(recommendationRequest.getImprovement())
                .recomandation(recommendationRequest.getRecomandation())
                .safety(recommendationRequest.getSafety())
                .build();

        Recommendation savedRecommendation = recommendationRepository.save(recommendation);
        return mapToResponseRecommendation(savedRecommendation);
    }

    private RecommendationResponse mapToResponseRecommendation(Recommendation savedRecommendation) {
        RecommendationResponse response = new RecommendationResponse();
        response.setType(savedRecommendation.getType());
        response.setSuggestion(savedRecommendation.getSuggestion());
        response.setImprovement(savedRecommendation.getImprovement());
        response.setRecomandation(savedRecommendation.getRecomandation());
        response.setSafety(savedRecommendation.getSafety());
        response.setId(savedRecommendation.getId());
        response.setCreatedAt(savedRecommendation.getCreatedAt());
        response.setUpdatedAt(savedRecommendation.getUpdatedAt());

        return response;
    }

    public List<RecommendationResponse> userRecommendation(String userId) {
       List<Recommendation> listRecommendation = recommendationRepository.findByUserId(userId);

       return listRecommendation.stream()
               .map(this::mapToResponseRecommendation)
               .collect(Collectors.toList());

    }

    public List<RecommendationResponse> activityRecommendation(String activityId) {

        List<Recommendation> listRecommendation = recommendationRepository.findByActivityId(activityId);
        return listRecommendation.stream()
                .map(this::mapToResponseRecommendation)
                .collect(Collectors.toList());
    }
}
