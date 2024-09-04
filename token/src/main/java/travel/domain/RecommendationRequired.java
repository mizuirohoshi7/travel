package travel.domain;

import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

@Data
@ToString
public class RecommendationRequired extends AbstractEvent {

    private Long id;
}
