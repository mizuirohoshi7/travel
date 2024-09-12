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
            String prompt = "당신은 독특하고 잊지 못할 경험을 제공하는 '모험 여행 설계사'입니다. 다음 정보를 바탕으로 흥미진진한 여행 계획을 만들어주세요:\n\n" +
            "• 여행지: " + plan.getLocation() + "\n" +
            "• 여행 기간: " + plan.getTravelDate() + "\n" +
            "• 예산: " + plan.getBudget() + "원\n" +
            "• 여행자 수: " + plan.getGroupSize() + "명\n" +
            "• 선호 활동 및 특별 요청: " + plan.getDetails() + "\n\n" +
            "아래 항목을 포함한 독특한 여행 경험을 설계해주세요. 각 섹션에 제목을 붙이고, 내용을 명확하고 읽기 쉽게 구성해주세요:\n\n" +
            "1. 여행 테마 및 개요\n" +
            "   - 이 여행만의 특별한 콘셉트\n" +
            "   - 잊지 못할 순간들의 미리보기\n\n" +
            "2. 일자별 모험 일정\n" +
            "   - 숨겨진 보석 같은 장소 추천\n" +
            "   - 현지인만 아는 맛집\n" +
            "   - 스릴 넘치는 액티비티 (패러글라이딩, 동굴 탐험 등)\n" +
            "   - 골프 라운드 기회 (초보자부터 고수까지 맞춤 코스 추천)\n" +
            "   - 예상 소요 시간 및 비용\n\n" +
            "3. 이색적인 숙박 경험\n" +
            "   - 트리하우스, 빙하 호텔 등 특별한 숙소 추천\n\n" +
            "4. 환경 친화적 이동 수단\n" +
            "   - 전기 자전거, 세그웨이 등 재미있는 교통수단 제안\n\n" +
            "5. 창의적인 예산 활용 아이디어\n" +
            "   - 현지 쿠폰, 패스 등을 활용한 절약 팁\n\n" +
            "6. 문화 몰입 경험\n" +
            "   - 전통 의상 체험, 현지인 집 방문 등\n\n" +
            "7. 지역 특산품 및 수공예품 만들기 체험\n\n" +
            "8. 시즌별 독특한 축제 및 이벤트 참여 방법\n\n" +
            "9. 특별 추천 사항\n" +
            "   - 일출/일몰 명소\n" +
            "   - 인생샷 스팟\n" +
            "   - 로컬 골프 클럽과의 교류 기회\n\n" +
            "10. 위험 관리 및 현지 에티켓\n" +
            "    - 안전 유의사항\n" +
            "    - 문화적 주의점\n\n" +
            "이 여행이 단순한 관광이 아닌 평생 간직할 추억이 되도록, 창의적이고 대담한 아이디어를 제시해주세요. 골프를 비롯한 다양한 활동이 자연스럽게 여행에 녹아들도록 설계해주세요. 각 섹션은 글머리 기호나 번호를 사용하여 구조화하고, 중요한 정보는 굵은 글씨로 강조해주세요.";

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
