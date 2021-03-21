package uz.itcenter.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.itcenter.service.dto.TeacherDTO;

/**
 * Service Interface for managing {@link uz.itcenter.domain.Teacher}.
 */
public interface TeacherService {
    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save.
     * @return the persisted entity.
     */
    TeacherDTO save(TeacherDTO teacherDTO);

    /**
     * Get all the teachers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TeacherDTO> findAll(Pageable pageable);

    /**
     * Get all the teachers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<TeacherDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" teacher.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeacherDTO> findOne(Long id);

    /**
     * Delete the "id" teacher.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<TeacherDTO> findByUserIsCurrentUser();
}
