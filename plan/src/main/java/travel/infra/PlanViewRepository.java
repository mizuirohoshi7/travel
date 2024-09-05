package travel.infra;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import travel.domain.*;

@RepositoryRestResource(collectionResourceRel = "planViews", path = "planViews")
public interface PlanViewRepository
    extends PagingAndSortingRepository<PlanView, Long> {}
