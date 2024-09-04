package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import travel.FollowApplication;
import travel.domain.FollowToggled;

@Entity
@Table(name = "Follow_table")
@Data
//<<< DDD / Aggregate Root
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long fromId;

    private Long toId;

    @PostPersist
    public void onPostPersist() {
        FollowToggled followToggled = new FollowToggled(this);
        followToggled.publishAfterCommit();
    }

    public static FollowRepository repository() {
        FollowRepository followRepository = FollowApplication.applicationContext.getBean(
            FollowRepository.class
        );
        return followRepository;
    }
}
//>>> DDD / Aggregate Root
