package com.MrEnineer.Gym.Service;

import com.MrEnineer.Gym.Entity.Activity;
import com.MrEnineer.Gym.Entity.User;
import com.MrEnineer.Gym.Repository.ActivityRepo;
import com.MrEnineer.Gym.Repository.UserRepository;
import com.MrEnineer.Gym.dto.ActivityRequest;
import com.MrEnineer.Gym.dto.ActivityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final UserRepository  userRepository ;
    private final ActivityRepo activityRepo;

    public ActivityResponse saveActivity(ActivityRequest request) {

        User user1 = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Invalide User :"+request.getUserId()));

        Activity activity1 = Activity.builder()
                .user(user1)
                .type(request.getType())
                .additionaMatrices(request.getAdditionaMatrices())
                .duration(request.getDuration())
                .caloriesBurn(request.getCaloriesBurn())
                .startTime(request.getStartTime())
                .build();
        Activity savedActivity = activityRepo.save(activity1);

        return mapToResponseActivity(savedActivity);
    }

    private ActivityResponse mapToResponseActivity(Activity savedActivity) {
        ActivityResponse response =new ActivityResponse();
        response.setId(savedActivity.getId());
        response.setCreatedAt(savedActivity.getCreatedAt());
        response.setUpdatedAt(savedActivity.getUpdatedAt());
        response.setType(savedActivity.getType());
        response.setAdditionaMatrices(savedActivity.getAdditionaMatrices());
        response.setDuration(savedActivity.getDuration());
        response.setCaloriesBurn((savedActivity.getCaloriesBurn()));
        response.setStartTime(savedActivity.getStartTime());
        response.setUserId(savedActivity.getUser().getId());
        return response;
    }

    public List<ActivityResponse> getUserActivity(String userId) {
        List<Activity> activityList= activityRepo.findByUserId(userId);

        return activityList.stream()
                .map(this :: mapToResponseActivity)
                .collect(Collectors.toList());
    }
}
