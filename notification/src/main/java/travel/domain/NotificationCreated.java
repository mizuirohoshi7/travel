package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class NotificationCreated extends AbstractEvent {

    private Long id;

    public NotificationCreated(Notification aggregate) {
        super(aggregate);
    }

    public NotificationCreated() {
        super();
    }
}
//>>> DDD / Domain Event
