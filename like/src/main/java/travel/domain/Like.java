package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import travel.LikeApplication;

@Entity
@Table(name = "Like_table")
@Data
//<<< DDD / Aggregate Root
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long memberId;

    private Long planId;

    private Long planOwnerId;

    @PostPersist
    public void onPostPersist() {
        LikeCreated likeCreated = new LikeCreated(this);
        likeCreated.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        LikeDeleted likeDeleted = new LikeDeleted(this);
        likeDeleted.publishAfterCommit();
    }

    public static LikeRepository repository() {
        LikeRepository likeRepository = LikeApplication.applicationContext.getBean(
            LikeRepository.class
        );
        return likeRepository;
    }
}
//>>> DDD / Aggregate Root
