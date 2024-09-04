package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class TokenDecreaseFailed extends AbstractEvent {

    private Long id;

    public TokenDecreaseFailed(Member aggregate) {
        super(aggregate);
    }

    public TokenDecreaseFailed() {
        super();
    }
}
//>>> DDD / Domain Event
