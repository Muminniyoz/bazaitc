package uz.itcenter.repository;

import uz.itcenter.domain.CoursePlan;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CoursePlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoursePlanRepository extends JpaRepository<CoursePlan, Long>, JpaSpecificationExecutor<CoursePlan> {
}
