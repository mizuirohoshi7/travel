package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class FollowCreated extends AbstractEvent {

    private Long id;
    private Long fromId;
    private Long toId;

    public FollowCreated(Follow aggregate) {
        super(aggregate);
    }

    public FollowCreated() {
        super();
    }
}
//>>> DDD / Domain Event
