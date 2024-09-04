package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class FollowToggled extends AbstractEvent {

    private Long id;

    public FollowToggled(Follow aggregate) {
        super(aggregate);
    }

    public FollowToggled() {
        super();
    }
}
//>>> DDD / Domain Event
