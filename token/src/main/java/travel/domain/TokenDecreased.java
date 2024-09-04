package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class TokenDecreased extends AbstractEvent {

    private Long id;

    public TokenDecreased(Member aggregate) {
        super(aggregate);
    }

    public TokenDecreased() {
        super();
    }
}
//>>> DDD / Domain Event
