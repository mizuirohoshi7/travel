package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "PlanView_table")
@Data
public class PlanView {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long memberId;
    private Integer groupSize;
    private String location;
    private Date travelDate;
    private Integer budget;
    private String details;
    private String aiRecommendation;
    private String memberName;
}
