package uz.itcenter.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.itcenter.service.dto.CenterDTO;

/**
 * Service Interface for managing {@link uz.itcenter.domain.Center}.
 */
public interface CenterService {
    /**
     * Save a center.
     *
     * @param centerDTO the entity to save.
     * @return the persisted entity.
     */
    CenterDTO save(CenterDTO centerDTO);

    /**
     * Get all the centers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CenterDTO> findAll(Pageable pageable);

    /**
     * Get the "id" center.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CenterDTO> findOne(Long id);

    /**
     * Delete the "id" center.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<CenterDTO> findAllowedCenter();
}
