package uz.itcenter.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.undertow.util.BadRequestException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.itcenter.security.AuthoritiesConstants;
import uz.itcenter.security.SecurityUtils;
import uz.itcenter.service.*;
import uz.itcenter.service.dto.CenterDTO;
import uz.itcenter.service.dto.CourseCriteria;
import uz.itcenter.service.dto.CourseDTO;
import uz.itcenter.service.dto.TeacherDTO;
import uz.itcenter.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.itcenter.domain.Course}.
 */
@RestController
@RequestMapping("/api")
public class CourseResource {
    private final Logger log = LoggerFactory.getLogger(CourseResource.class);

    private static final String ENTITY_NAME = "course";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseService courseService;

    private final CourseQueryService courseQueryService;

    private final CenterService centerService;
    private final TeacherService teacherService;

    public CourseResource(
        CourseService courseService,
        CourseQueryService courseQueryService,
        CenterService centerService,
        TeacherService teacherService
    ) {
        this.courseService = courseService;
        this.courseQueryService = courseQueryService;

        this.centerService = centerService;
        this.teacherService = teacherService;
    }

    /**
     * {@code POST  /courses} : Create a new course.
     *
     * @param courseDTO the courseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseDTO, or with status {@code 400 (Bad Request)} if the course has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/courses")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\", \"" + AuthoritiesConstants.MANAGER + "\")")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) throws URISyntaxException, BadRequestException {
        log.debug("REST request to save Course : {}", courseDTO);
        if (courseDTO.getId() != null) {
            throw new BadRequestAlertException("A new course cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER)) {
            Optional<CenterDTO> allowedCenter = centerService.findAllowedCenter();
            if (!allowedCenter.isPresent() || (!courseDTO.getCenterId().equals(allowedCenter.get().getId()))) {
                throw new BadRequestAlertException("Invalid center id", ENTITY_NAME, "idnull");
            }
        }

        CourseDTO result = courseService.save(courseDTO);
        return ResponseEntity
            .created(new URI("/api/courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /courses} : Updates an existing course.
     *
     * @param courseDTO the courseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseDTO,
     * or with status {@code 400 (Bad Request)} if the courseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/courses")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\", \"" + AuthoritiesConstants.MANAGER + "\")")
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO) throws URISyntaxException {
        log.debug("REST request to update Course : {}", courseDTO);
        if (courseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER)) {
            Optional<CourseDTO> oldCourse = courseService.findOne(courseDTO.getId());

            Optional<CenterDTO> allowedCenter = centerService.findAllowedCenter();

            if (oldCourse.isPresent() && allowedCenter.isPresent() && !(oldCourse.get().getCenterId() == allowedCenter.get().getId())) {
                throw new BadRequestAlertException("Invalid center id", ENTITY_NAME, "idnull");
            }

            if (!allowedCenter.isPresent() || allowedCenter.isPresent() && !(courseDTO.getCenterId() == allowedCenter.get().getId())) {
                throw new BadRequestAlertException("Invalid center id", ENTITY_NAME, "idnull");
            }
        }

        CourseDTO result = courseService.save(courseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /courses} : get all the courses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courses in body.
     */
    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> getAllCourses(CourseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Courses by criteria: {}", criteria);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER)) {
            Optional<CenterDTO> allowedCenter = centerService.findAllowedCenter();

            if (!allowedCenter.isPresent()) {
                throw new BadRequestAlertException("Invalid center id", ENTITY_NAME, "idnull");
            }
            criteria.getCenterId().setEquals(allowedCenter.get().getId());
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.TEACHER)) {
            TeacherDTO t = this.teacherService.findByUserIsCurrentUser().orElse(null);
            if (t != null) {
                criteria.getTeacherId().setEquals(t.getId());
            } else {
                throw new BadRequestAlertException("Invalid center id", ENTITY_NAME, "idnull");
            }
        }

        Page<CourseDTO> page = courseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /courses/count} : count all the courses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/courses/count")
    public ResponseEntity<Long> countCourses(CourseCriteria criteria) {
        log.debug("REST request to count Courses by criteria: {}", criteria);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER)) {
            Optional<CenterDTO> allowedCenter = centerService.findAllowedCenter();

            if (!allowedCenter.isPresent()) {
                throw new BadRequestAlertException("Invalid center id", ENTITY_NAME, "idnull");
            }
            criteria.getCenterId().setEquals(allowedCenter.get().getId());
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.TEACHER)) {
            Optional<TeacherDTO> t = this.teacherService.findByUserIsCurrentUser();
            if (t.isPresent()) {
                criteria.getTeacherId().setEquals(t.get().getId());
            } else {
                throw new BadRequestAlertException("Invalid center id", ENTITY_NAME, "idnull");
            }
        }

        return ResponseEntity.ok().body(courseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /courses/:id} : get the "id" course.
     *
     * @param id the id of the courseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/courses/{id}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long id) {
        log.debug("REST request to get Course : {}", id);
        Optional<CourseDTO> courseDTO = courseService.findOne(id);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER)) {
            Optional<CenterDTO> allowedCenter = centerService.findAllowedCenter();
            if (
                !allowedCenter.isPresent() || courseDTO.isPresent() && (!courseDTO.get().getCenterId().equals(allowedCenter.get().getId()))
            ) {
                throw new BadRequestAlertException("Permisson denied", ENTITY_NAME, "idnull");
            }
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.TEACHER)) {
            Optional<TeacherDTO> t = this.teacherService.findByUserIsCurrentUser();
            if (
                !t.isPresent() || courseDTO.isPresent() && !(t.get().getId().equals(courseDTO.get().getTeacherId()))
            ) throw new BadRequestAlertException("Invalid center id", ENTITY_NAME, "idnull");
        }

        return ResponseUtil.wrapOrNotFound(courseDTO);
    }

    /**
     * {@code DELETE  /courses/:id} : delete the "id" course.
     *
     * @param id the id of the courseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/courses/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.debug("REST request to delete Course : {}", id);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER)) {
            Optional<CourseDTO> oldCourse = courseService.findOne(id);

            Optional<CenterDTO> allowedCenter = centerService.findAllowedCenter();

            if (
                !allowedCenter.isPresent() || oldCourse.isPresent() && (!oldCourse.get().getCenterId().equals(allowedCenter.get().getId()))
            ) {
                throw new BadRequestAlertException("Invalid center id", ENTITY_NAME, "idnull");
            }
        }

        courseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
