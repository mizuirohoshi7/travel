package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import travel.NotificationApplication;
import travel.domain.NotificationCreated;

@Entity
@Table(name = "Notification_table")
@Data
//<<< DDD / Aggregate Root
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long memberId;

    private String details;

    private Date createdAt;

    @PostPersist
    public void onPostPersist() {
        NotificationCreated notificationCreated = new NotificationCreated(this);
        notificationCreated.publishAfterCommit();
    }

    public static NotificationRepository repository() {
        NotificationRepository notificationRepository = NotificationApplication.applicationContext.getBean(
            NotificationRepository.class
        );
        return notificationRepository;
    }

    //<<< Clean Arch / Port Method
    public static void createNotification(FollowCreated followCreated) {
        Notification notification = new Notification();
        notification.setMemberId(followCreated.getToId());
        notification.setDetails(followCreated.getFromId() + "님이 " + followCreated.getToId() + "님을 팔로우했습니다.");
        notification.setCreatedAt(new Date());

        repository().save(notification);

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void createNotification(PlanCreated planCreated) {
        Notification notification = new Notification();

        Long planOwnerId = planCreated.getMemberId();

        // notification.setMemberId();
        notification.setDetails(planCreated.getMemberId() + "님이 새로운 여행 계획을 생성했습니다: " + planCreated.getLocation());
        notification.setCreatedAt(new Date());

        repository().save(notification);
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void createNotification(LikeCreated likeCreated) {
        Notification notification = new Notification();
        notification.setMemberId(likeCreated.getPlanOwnerId());
        notification.setDetails(likeCreated.getMemberId() + "님이 " + likeCreated.getPlanId() + "번 게시글에 좋아요를 눌렀습니다.");
        notification.setCreatedAt(new Date());

        repository().save(notification);
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
