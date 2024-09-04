package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PlanCreated extends AbstractEvent {

    private Long id;

    public PlanCreated(Plan aggregate) {
        super(aggregate);
    }

    public PlanCreated() {
        super();
    }
}
//>>> DDD / Domain Event
