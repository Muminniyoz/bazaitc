package uz.itcenter.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.itcenter.domain.Course;
import uz.itcenter.domain.Teacher;

/**
 * Spring Data  repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {}
