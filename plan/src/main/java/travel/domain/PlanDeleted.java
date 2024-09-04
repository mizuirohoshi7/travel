package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PlanDeleted extends AbstractEvent {

    private Long id;

    public PlanDeleted(Plan aggregate) {
        super(aggregate);
    }

    public PlanDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
