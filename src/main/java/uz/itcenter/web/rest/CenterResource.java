package uz.itcenter.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.itcenter.domain.User;
import uz.itcenter.security.AuthoritiesConstants;
import uz.itcenter.security.SecurityUtils;
import uz.itcenter.service.CenterQueryService;
import uz.itcenter.service.CenterService;
import uz.itcenter.service.UserService;
import uz.itcenter.service.dto.CenterCriteria;
import uz.itcenter.service.dto.CenterDTO;
import uz.itcenter.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.itcenter.domain.Center}.
 */
@RestController
@RequestMapping("/api")
public class CenterResource {
    private final Logger log = LoggerFactory.getLogger(CenterResource.class);

    private static final String ENTITY_NAME = "center";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CenterService centerService;
    private final UserService userService;

    private final CenterQueryService centerQueryService;

    public CenterResource(CenterService centerService, UserService userService, CenterQueryService centerQueryService) {
        this.centerService = centerService;
        this.userService = userService;
        this.centerQueryService = centerQueryService;
    }

    /**
     * {@code POST  /centers} : Create a new center.
     *
     * @param centerDTO the centerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centerDTO, or with status {@code 400 (Bad Request)} if the center has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/centers")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CenterDTO> createCenter(@Valid @RequestBody CenterDTO centerDTO) throws URISyntaxException {
        log.debug("REST request to save Center : {}", centerDTO);
        if (centerDTO.getId() != null) {
            throw new BadRequestAlertException("A new center cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CenterDTO result = centerService.save(centerDTO);
        return ResponseEntity
            .created(new URI("/api/centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /centers} : Updates an existing center.
     *
     * @param centerDTO the centerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centerDTO,
     * or with status {@code 400 (Bad Request)} if the centerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/centers")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CenterDTO> updateCenter(@Valid @RequestBody CenterDTO centerDTO) throws URISyntaxException {
        log.debug("REST request to update Center : {}", centerDTO);
        if (centerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CenterDTO result = centerService.save(centerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /centers} : get all the centers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centers in body.
     */
    @GetMapping("/centers")
    public ResponseEntity<List<CenterDTO>> getAllCenters(CenterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Centers by criteria: {}", criteria);
        Page<CenterDTO> page = centerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/centers/allowed")
    public ResponseEntity<CenterDTO> getAllowedCenters() {
        log.debug("REST request to get Centers by criteria: ");
        User u = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        CenterDTO center = centerService.findAllowedCenter().orElse(null);
        return ResponseEntity.ok().body(center);
    }

    /**
     * {@code GET  /centers/count} : count all the centers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/centers/count")
    public ResponseEntity<Long> countCenters(CenterCriteria criteria) {
        log.debug("REST request to count Centers by criteria: {}", criteria);
        return ResponseEntity.ok().body(centerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /centers/:id} : get the "id" center.
     *
     * @param id the id of the centerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/centers/{id}")
    public ResponseEntity<CenterDTO> getCenter(@PathVariable Long id) {
        log.debug("REST request to get Center : {}", id);
        Optional<CenterDTO> centerDTO = centerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centerDTO);
    }

    /**
     * {@code DELETE  /centers/:id} : delete the "id" center.
     *
     * @param id the id of the centerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/centers/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteCenter(@PathVariable Long id) {
        log.debug("REST request to delete Center : {}", id);
        centerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
