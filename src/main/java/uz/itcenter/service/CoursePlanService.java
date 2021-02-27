package uz.itcenter.service;

import uz.itcenter.service.dto.CoursePlanDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.itcenter.domain.CoursePlan}.
 */
public interface CoursePlanService {

    /**
     * Save a coursePlan.
     *
     * @param coursePlanDTO the entity to save.
     * @return the persisted entity.
     */
    CoursePlanDTO save(CoursePlanDTO coursePlanDTO);

    /**
     * Get all the coursePlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CoursePlanDTO> findAll(Pageable pageable);


    /**
     * Get the "id" coursePlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CoursePlanDTO> findOne(Long id);

    /**
     * Delete the "id" coursePlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
