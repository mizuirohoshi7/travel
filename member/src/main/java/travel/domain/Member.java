package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import travel.MemberApplication;
import travel.domain.MemberCreated;
import travel.domain.TokenDecreased;
import travel.domain.TokenDecreasingFailed;
import travel.domain.TokenIncreased;

@Entity
@Table(name = "Member_table")
@Data
//<<< DDD / Aggregate Root
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String oauthId;

    private String name;

    private String email;

    private Integer tokenAmount = 5000;

    @PostPersist
    public void onPostPersist() {
        MemberCreated memberCreated = new MemberCreated(this);
        memberCreated.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        TokenIncreased tokenIncreased = new TokenIncreased(this);
        tokenIncreased.publishAfterCommit();
    }

    public static MemberRepository repository() {
        MemberRepository memberRepository = MemberApplication.applicationContext.getBean(
            MemberRepository.class
        );
        return memberRepository;
    }

    //<<< Clean Arch / Port Method
    public static void decreaseToken(
        RecommendationRequired recommendationRequired
    ) {
        Member member = repository().findById(recommendationRequired.getMemberId()).get();
        int currentToken = member.getTokenAmount();
        int requiredToken = 100; // 요구양에 따라 변화 필요

        if (currentToken >= requiredToken) {
            member.setTokenAmount(currentToken - requiredToken);
            TokenDecreased tokenDecreased = new TokenDecreased(member);
            tokenDecreased.publishAfterCommit();
        } else {
            TokenDecreasingFailed tokenDecreasingFailed = new TokenDecreasingFailed(member);
            tokenDecreasingFailed.publishAfterCommit();
        }
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root

