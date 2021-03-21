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
import uz.itcenter.service.RegionsQueryService;
import uz.itcenter.service.RegionsService;
import uz.itcenter.service.UserService;
import uz.itcenter.service.dto.CenterDTO;
import uz.itcenter.service.dto.RegionsCriteria;
import uz.itcenter.service.dto.RegionsDTO;
import uz.itcenter.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.itcenter.domain.Regions}.
 */
@RestController
@RequestMapping("/api")
public class RegionsResource {
    private final Logger log = LoggerFactory.getLogger(RegionsResource.class);

    private static final String ENTITY_NAME = "regions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegionsService regionsService;

    private final RegionsQueryService regionsQueryService;
    private final UserService userService;

    public RegionsResource(RegionsService regionsService, RegionsQueryService regionsQueryService, UserService userService) {
        this.regionsService = regionsService;
        this.regionsQueryService = regionsQueryService;
        this.userService = userService;
    }

    /**
     * {@code POST  /regions} : Create a new regions.
     *
     * @param regionsDTO the regionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regionsDTO, or with status {@code 400 (Bad Request)} if the regions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<RegionsDTO> createRegions(@Valid @RequestBody RegionsDTO regionsDTO) throws URISyntaxException {
        log.debug("REST request to save Regions : {}", regionsDTO);
        if (regionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new regions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegionsDTO result = regionsService.save(regionsDTO);
        return ResponseEntity
            .created(new URI("/api/regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regions} : Updates an existing regions.
     *
     * @param regionsDTO the regionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regionsDTO,
     * or with status {@code 400 (Bad Request)} if the regionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<RegionsDTO> updateRegions(@Valid @RequestBody RegionsDTO regionsDTO) throws URISyntaxException {
        log.debug("REST request to update Regions : {}", regionsDTO);
        if (regionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegionsDTO result = regionsService.save(regionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /regions} : get all the regions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regions in body.
     */
    @GetMapping("/regions")
    public ResponseEntity<List<RegionsDTO>> getAllRegions(RegionsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Regions by criteria: {}", criteria);
        Page<RegionsDTO> page = regionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/regions/allowed")
    public ResponseEntity<RegionsDTO> getAllowedCenters() {
        log.debug("REST request to get Centers by criteria: ");
        User u = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        RegionsDTO regionsDTO = regionsService.findAllowedRegions().orElse(null);
        return ResponseEntity.ok().body(regionsDTO);
    }

    /**
     * {@code GET  /regions/count} : count all the regions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/regions/count")
    public ResponseEntity<Long> countRegions(RegionsCriteria criteria) {
        log.debug("REST request to count Regions by criteria: {}", criteria);
        return ResponseEntity.ok().body(regionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /regions/:id} : get the "id" regions.
     *
     * @param id the id of the regionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regions/{id}")
    public ResponseEntity<RegionsDTO> getRegions(@PathVariable Long id) {
        log.debug("REST request to get Regions : {}", id);
        Optional<RegionsDTO> regionsDTO = regionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regionsDTO);
    }

    /**
     * {@code DELETE  /regions/:id} : delete the "id" regions.
     *
     * @param id the id of the regionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regions/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteRegions(@PathVariable Long id) {
        log.debug("REST request to delete Regions : {}", id);
        regionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
