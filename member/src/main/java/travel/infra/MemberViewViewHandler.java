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
public class MemberViewViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MemberViewRepository memberViewRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenMemberCreated_then_CREATE_1(
        @Payload MemberCreated memberCreated
    ) {
        try {
            if (!memberCreated.validate()) return;

            // view 객체 생성
            MemberView memberView = new MemberView();
            // view 객체에 이벤트의 Value 를 set 함
            memberView.setId(memberCreated.getId());
            memberView.setOauthId(memberCreated.getOauthId());
            memberView.setName(memberCreated.getName());
            memberView.setEmail(memberCreated.getEmail());
            memberView.setTokenAmount(memberCreated.getTokenAmount());
            // view 레파지 토리에 save
            memberViewRepository.save(memberView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenTokenIncreased_then_UPDATE_1(
        @Payload TokenIncreased tokenIncreased
    ) {
        try {
            if (!tokenIncreased.validate()) return;
            // view 객체 조회
            Optional<MemberView> memberViewOptional = memberViewRepository.findById(
                tokenIncreased.getId()
            );

            if (memberViewOptional.isPresent()) {
                MemberView memberView = memberViewOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                memberView.setTokenAmount(tokenIncreased.getTokenAmount());
                // view 레파지 토리에 save
                memberViewRepository.save(memberView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenTokenDecreased_then_UPDATE_2(
        @Payload TokenDecreased tokenDecreased
    ) {
        try {
            if (!tokenDecreased.validate()) return;
            // view 객체 조회
            Optional<MemberView> memberViewOptional = memberViewRepository.findById(
                tokenDecreased.getId()
            );

            if (memberViewOptional.isPresent()) {
                MemberView memberView = memberViewOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                memberView.setTokenAmount(tokenDecreased.getTokenAmount());
                // view 레파지 토리에 save
                memberViewRepository.save(memberView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
