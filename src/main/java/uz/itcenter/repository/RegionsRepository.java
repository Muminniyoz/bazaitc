package uz.itcenter.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.itcenter.domain.Regions;

/**
 * Spring Data  repository for the Regions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionsRepository extends JpaRepository<Regions, Long>, JpaSpecificationExecutor<Regions> {
    @Query("select regions from Regions regions where regions.director.login = ?#{principal.username}")
    Optional<Regions> findByDirectorIsCurrentUser();
}
