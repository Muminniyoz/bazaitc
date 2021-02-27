package uz.itcenter.repository;

import uz.itcenter.domain.Regions;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Regions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionsRepository extends JpaRepository<Regions, Long>, JpaSpecificationExecutor<Regions> {

    @Query("select regions from Regions regions where regions.director.login = ?#{principal.username}")
    List<Regions> findByDirectorIsCurrentUser();
}
