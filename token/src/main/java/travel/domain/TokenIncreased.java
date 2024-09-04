package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class TokenIncreased extends AbstractEvent {

    private Long id;

    public TokenIncreased(Member aggregate) {
        super(aggregate);
    }

    public TokenIncreased() {
        super();
    }
}
//>>> DDD / Domain Event
