package uz.itcenter.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.itcenter.domain.Center;

/**
 * Spring Data  repository for the Center entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CenterRepository extends JpaRepository<Center, Long>, JpaSpecificationExecutor<Center> {
    @Query("select center from Center center where center.modifiedBy.login = ?#{principal.username}")
    Optional<List<Center>> findByModifiedByIsCurrentUser();

    @Query("select center from Center center where center.manager.login = ?#{principal.username}")
    Optional<Center> findByManagerIsCurrentUser();
}
