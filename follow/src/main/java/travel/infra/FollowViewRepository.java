package travel.infra;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import travel.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "followViews",
    path = "followViews"
)
public interface FollowViewRepository
    extends PagingAndSortingRepository<FollowView, Long> {}
