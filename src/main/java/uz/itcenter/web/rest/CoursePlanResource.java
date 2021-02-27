package uz.itcenter.web.rest;

import uz.itcenter.service.CoursePlanService;
import uz.itcenter.web.rest.errors.BadRequestAlertException;
import uz.itcenter.service.dto.CoursePlanDTO;
import uz.itcenter.service.dto.CoursePlanCriteria;
import uz.itcenter.service.CoursePlanQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uz.itcenter.domain.CoursePlan}.
 */
@RestController
@RequestMapping("/api")
public class CoursePlanResource {

    private final Logger log = LoggerFactory.getLogger(CoursePlanResource.class);

    private static final String ENTITY_NAME = "coursePlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoursePlanService coursePlanService;

    private final CoursePlanQueryService coursePlanQueryService;

    public CoursePlanResource(CoursePlanService coursePlanService, CoursePlanQueryService coursePlanQueryService) {
        this.coursePlanService = coursePlanService;
        this.coursePlanQueryService = coursePlanQueryService;
    }

    /**
     * {@code POST  /course-plans} : Create a new coursePlan.
     *
     * @param coursePlanDTO the coursePlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coursePlanDTO, or with status {@code 400 (Bad Request)} if the coursePlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-plans")
    public ResponseEntity<CoursePlanDTO> createCoursePlan(@RequestBody CoursePlanDTO coursePlanDTO) throws URISyntaxException {
        log.debug("REST request to save CoursePlan : {}", coursePlanDTO);
        if (coursePlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new coursePlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoursePlanDTO result = coursePlanService.save(coursePlanDTO);
        return ResponseEntity.created(new URI("/api/course-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-plans} : Updates an existing coursePlan.
     *
     * @param coursePlanDTO the coursePlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coursePlanDTO,
     * or with status {@code 400 (Bad Request)} if the coursePlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coursePlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-plans")
    public ResponseEntity<CoursePlanDTO> updateCoursePlan(@RequestBody CoursePlanDTO coursePlanDTO) throws URISyntaxException {
        log.debug("REST request to update CoursePlan : {}", coursePlanDTO);
        if (coursePlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CoursePlanDTO result = coursePlanService.save(coursePlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coursePlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /course-plans} : get all the coursePlans.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coursePlans in body.
     */
    @GetMapping("/course-plans")
    public ResponseEntity<List<CoursePlanDTO>> getAllCoursePlans(CoursePlanCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CoursePlans by criteria: {}", criteria);
        Page<CoursePlanDTO> page = coursePlanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-plans/count} : count all the coursePlans.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-plans/count")
    public ResponseEntity<Long> countCoursePlans(CoursePlanCriteria criteria) {
        log.debug("REST request to count CoursePlans by criteria: {}", criteria);
        return ResponseEntity.ok().body(coursePlanQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-plans/:id} : get the "id" coursePlan.
     *
     * @param id the id of the coursePlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coursePlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-plans/{id}")
    public ResponseEntity<CoursePlanDTO> getCoursePlan(@PathVariable Long id) {
        log.debug("REST request to get CoursePlan : {}", id);
        Optional<CoursePlanDTO> coursePlanDTO = coursePlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coursePlanDTO);
    }

    /**
     * {@code DELETE  /course-plans/:id} : delete the "id" coursePlan.
     *
     * @param id the id of the coursePlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-plans/{id}")
    public ResponseEntity<Void> deleteCoursePlan(@PathVariable Long id) {
        log.debug("REST request to delete CoursePlan : {}", id);
        coursePlanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
