package com.MrEnineer.Gym.Service;

import com.MrEnineer.Gym.Entity.Activity;
import com.MrEnineer.Gym.Entity.Recommendation;
import com.MrEnineer.Gym.Entity.User;
import com.MrEnineer.Gym.Repository.ActivityRepo;
import com.MrEnineer.Gym.Repository.RecommendationRepository;
import com.MrEnineer.Gym.Repository.UserRepository;
import com.MrEnineer.Gym.dto.RecommendationRequest;
import com.MrEnineer.Gym.dto.RecommendationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final ActivityRepo activityRepo;
    private final WebClient webClient;
    private final String apikey;

    public RecommendationService(
            RecommendationRepository recommendationRepository, UserRepository userRepository, ActivityRepo activityRepo, WebClient.Builder webClientBuilder,
            @Value("${groq.api.url}") String baseUrl,
            @Value("${groq.api.key}") String geminiApiKey
    ) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.activityRepo = activityRepo;
        this.apikey = geminiApiKey;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public RecommendationResponse generate(RecommendationRequest recommendationRequest) throws JsonProcessingException {


        // Build Prompt
        String prompt = buildPrompt(recommendationRequest);

        // Prepare raw JSON BODY
        Map<String, Object> requestBody =
                Map.of(
                        "model", "llama-3.3-70b-versatile",
                        "messages",
                        List.of(
                                Map.of(
                                        "role", "user",
                                        "content", prompt
                                )
                        )
                );
        System.out.println(requestBody);
        // Send Request
        String response = webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apikey)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        clientResponse ->
                                clientResponse.bodyToMono(String.class)
                                        .map(error -> {
                                            System.out.println("GROQ ERROR:");
                                            System.out.println(error);
                                            return new RuntimeException(error);
                                        })
                )
                .bodyToMono(String.class)
                .block();

        // Extract Response
        String aiJson = extractResponseContent(response);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = mapper.readTree(aiJson);
        String recommendationString =
                node.get("recommendation").asText();
        List<String> suggestions =
                mapper.convertValue(
                        node.get("suggestions"),
                        new TypeReference<List<String>>() {}
                );
        List<String> improvements =
                mapper.convertValue(
                        node.get("improvements"),
                        new TypeReference<List<String>>() {}
                );
        List<String> safety =
                mapper.convertValue(
                        node.get("safety"),
                        new TypeReference<List<String>>() {}
                );

        Recommendation recommendation = mapToRecommendation(recommendationRequest,recommendationString,suggestions,improvements,safety);

        Recommendation savedRecommendation = recommendationRepository.save(recommendation);
        return mapToResponseRecommendation(savedRecommendation);
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

    private String extractResponseContent(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            String content = root.path("choices")
                    .path(0)
                    .path("message")
                    .path("content")
                    .asText();

            content = content.replace("```json", "");
            content = content.replace("```", "");

            return content.trim();

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response", e);
        }
    }

    private String buildPrompt(RecommendationRequest recommendationRequest) {
        StringBuilder prompt = new StringBuilder();

        User user = userRepository.findById(recommendationRequest.getUserId())
                .orElseThrow(()->new RuntimeException("User Not Found "+recommendationRequest.getUserId()));

        Activity activity = activityRepo.findById(recommendationRequest.getActivityId())
                .orElseThrow(()->new RuntimeException("Activity Not Found "+ recommendationRequest.getActivityId()));

        prompt.append("You are an expert fitness coach. ");
        prompt.append("\n" +
                "User Details:\n" +
                "Age: " +user.getAge()+ "\n"+
                "Weight: " + user.getWeight()+"kg \n" +
                "Height: "+user.getHeight()+"cm");
        prompt.append("\n" +
                "Activity Details:\n" +
                "Type: " +activity.getType()+ "\n"+
                "Duration: " + activity.getDuration()+"minute \n" +
                "Calories Burned: "+activity.getCaloriesBurn()+"calories"+
                "\nAdditional Metrics: "
                        + activity.getAdditionaMatrices());
        prompt.append("Generate:\n" +
                        "\n" +
                        "1. Recommendation\n" +
                        "2. 3 Suggestions\n" +
                        "3. 3 Improvements\n" +
                        "4. 3 Safety Tips\n" +
                        "\n" );
        prompt.append("""
        Return ONLY valid JSON.
        
        {
          "recommendation":"string",
          "suggestions":["string","string","string"],
          "improvements":["string","string","string"],
          "safety":["string","string","string"]
        }
        
        Do not return markdown.
        Do not return explanation.
        """);
        return prompt.toString();
    }


    private RecommendationResponse mapToResponseRecommendation(Recommendation savedRecommendation) {

        RecommendationResponse response = new RecommendationResponse();
        response.setSuggestion(savedRecommendation.getSuggestion());
        response.setImprovement(savedRecommendation.getImprovement());
        response.setType(savedRecommendation.getType());
        response.setRecomandation(savedRecommendation.getRecomandation());
        response.setSafety(savedRecommendation.getSafety());
        response.setId(savedRecommendation.getId());
        response.setCreatedAt(savedRecommendation.getCreatedAt());
        response.setUpdatedAt(savedRecommendation.getUpdatedAt());

        return response;
    }

    private Recommendation mapToRecommendation(RecommendationRequest recommendationRequest,String recom,List<String> suggestion,List<String> improvement,List<String> safety){
        User user = userRepository.findById(recommendationRequest.getUserId())
                .orElseThrow(()->new RuntimeException("User Not Found "+recommendationRequest.getUserId()));

        Activity activity = activityRepo.findById(recommendationRequest.getActivityId())
                .orElseThrow(()->new RuntimeException("Activity Not Found "+ recommendationRequest.getActivityId()));

        return Recommendation.builder()
                .user(user)
                .activity(activity)
                .type(activity.getType().name())
                .suggestion(suggestion)
                .improvement(improvement)
                .recomandation(recom)
                .safety(safety)
                .build();
    }
}
