package travel.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "follows", path = "follows")
public interface FollowRepository extends PagingAndSortingRepository<Follow, Long> {

    // Custom query to find all follow relationships by the follower's ID (fromId)
    List<Follow> findByFromId(Long fromId);
}
