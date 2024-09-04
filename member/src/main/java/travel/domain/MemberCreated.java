package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class MemberCreated extends AbstractEvent {

    private Long id;

    public MemberCreated(Member aggregate) {
        super(aggregate);
    }

    public MemberCreated() {
        super();
    }
}
//>>> DDD / Domain Event
