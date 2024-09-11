package travel.domain;

import java.util.*;
import javax.persistence.*;
import lombok.Data;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import travel.PlanApplication;

@Entity
@Table(name = "Plan_table")
@Data
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long memberId;
    private String location;
    private Date travelDate;
    private Integer budget;
    private Integer groupSize;
    private String details;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String aiRecommendation;

    private static Long lastRequestedPlanId;

    @PostPersist
    public void onPostPersist() {
        RecommendationCreated recommendationCreated = new RecommendationCreated(this);
        recommendationCreated.publishAfterCommit();

        PlanCreated planCreated = new PlanCreated(this);
        planCreated.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        PlanUpdated planUpdated = new PlanUpdated(this);
        planUpdated.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        PlanDeleted planDeleted = new PlanDeleted(this);
        planDeleted.publishAfterCommit();
    }

    public static PlanRepository repository() {
        return PlanApplication.applicationContext.getBean(PlanRepository.class);
    }

    public void requireRecommendation() {
        RecommendationRequired recommendationRequired = new RecommendationRequired(this);
        recommendationRequired.publishAfterCommit();

        // 현재 Plan의 ID를 임시 저장
        lastRequestedPlanId = this.getId();
    }

    public static void createRecommendation(TokenDecreased tokenDecreased) {
        if (lastRequestedPlanId == null) {
            System.out.println("[오류] 최근 요청된 Plan ID를 찾을 수 없습니다.");
            return;
        }

        Optional<Plan> planOptional = repository().findById(lastRequestedPlanId);
        if (!planOptional.isPresent()) {
            System.out.println("[오류] Plan을 찾을 수 없습니다. Plan ID: " + lastRequestedPlanId);
            return;
        }
        Plan plan = planOptional.get();

        String openAiToken = "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";

        try {
            String prompt = "당신은 개인 맞춤형 여행 계획 전문가입니다. 다음 정보를 바탕으로 상세한 여행 계획을 제안해주세요:\n" +
                "위치: " + plan.getLocation() + "\n" +
                "여행 날짜: " + plan.getTravelDate() + "\n" +
                "예산: " + plan.getBudget() + "원\n" +
                "그룹 크기: " + plan.getGroupSize() + "명\n" +
                "추가 세부사항: " + plan.getDetails() + "\n\n" +
                "다음 형식으로 응답해주세요:\n" +
                "1. 여행 개요\n" +
                "2. 일자별 세부 계획 (각 일자마다):\n" +
                "   - 추천 활동 및 관광지\n" +
                "   - 식사 추천\n" +
                "   - 예상 소요 시간 및 비용\n" +
                "3. 숙박 추천\n" +
                "4. 교통 수단 추천\n" +
                "5. 예산 분배 제안\n" +
                "6. 현지 문화 및 주의사항 팁\n" +
                "7. 추가 추천 사항 (선택적 활동, 쇼핑, 등)";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(openAiToken);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo-0125");
            requestBody.put("messages", Arrays.asList(
                Map.of("role", "system", "content", "You are a travel planning expert."),
                Map.of("role", "user", "content", prompt)
            ));
            requestBody.put("max_tokens", 1000);
            requestBody.put("temperature", 0.7);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions",
                entity,
                Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            
            if (choices.isEmpty()) {
                throw new IllegalStateException("[오류] API 응답에 선택 항목이 없습니다.");
            }

            String recommendation = (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");
            System.out.println("생성된 추천: " + recommendation);

            plan.setAiRecommendation(recommendation.trim());
            repository().save(plan);

            RecommendationCreated recommendationCreated = new RecommendationCreated(plan);
            recommendationCreated.publishAfterCommit();

            System.out.println("AI 추천이 성공적으로 생성되고 저장되었습니다. Plan ID: " + plan.getId());
            lastRequestedPlanId = null;

        } catch (Exception e) {
            System.out.println("[오류] AI 추천 생성 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            plan.setAiRecommendation("죄송합니다. 현재 AI 추천을 생성할 수 없습니다. 나중에 다시 시도해 주세요.");
            repository().save(plan);
            lastRequestedPlanId = null;
        }
    }
}
