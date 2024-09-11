package travel.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import travel.config.kafka.KafkaProcessor;
import travel.domain.*;

@Service
public class PlanViewViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private PlanViewRepository planViewRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPlanCreated_then_CREATE_1(
        @Payload PlanCreated planCreated
    ) {
        try {
            if (!planCreated.validate()) return;

            // view 객체 생성
            PlanView planView = new PlanView();
            // view 객체에 이벤트의 Value 를 set 함
            planView.setId(planCreated.getId());
            planView.setMemberName(String.valueOf(planCreated.getMemberId()));
            // view 레파지 토리에 save
            planViewRepository.save(planView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
