package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class RecommendationCreated extends AbstractEvent {

    private Long id;

    public RecommendationCreated(Plan aggregate) {
        super(aggregate);
    }

    public RecommendationCreated() {
        super();
    }
}
//>>> DDD / Domain Event
