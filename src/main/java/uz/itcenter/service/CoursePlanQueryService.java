package uz.itcenter.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import uz.itcenter.domain.CoursePlan;
import uz.itcenter.domain.*; // for static metamodels
import uz.itcenter.repository.CoursePlanRepository;
import uz.itcenter.service.dto.CoursePlanCriteria;
import uz.itcenter.service.dto.CoursePlanDTO;
import uz.itcenter.service.mapper.CoursePlanMapper;

/**
 * Service for executing complex queries for {@link CoursePlan} entities in the database.
 * The main input is a {@link CoursePlanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CoursePlanDTO} or a {@link Page} of {@link CoursePlanDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CoursePlanQueryService extends QueryService<CoursePlan> {

    private final Logger log = LoggerFactory.getLogger(CoursePlanQueryService.class);

    private final CoursePlanRepository coursePlanRepository;

    private final CoursePlanMapper coursePlanMapper;

    public CoursePlanQueryService(CoursePlanRepository coursePlanRepository, CoursePlanMapper coursePlanMapper) {
        this.coursePlanRepository = coursePlanRepository;
        this.coursePlanMapper = coursePlanMapper;
    }

    /**
     * Return a {@link List} of {@link CoursePlanDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CoursePlanDTO> findByCriteria(CoursePlanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CoursePlan> specification = createSpecification(criteria);
        return coursePlanMapper.toDto(coursePlanRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CoursePlanDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CoursePlanDTO> findByCriteria(CoursePlanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CoursePlan> specification = createSpecification(criteria);
        return coursePlanRepository.findAll(specification, page)
            .map(coursePlanMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CoursePlanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CoursePlan> specification = createSpecification(criteria);
        return coursePlanRepository.count(specification);
    }

    /**
     * Function to convert {@link CoursePlanCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CoursePlan> createSpecification(CoursePlanCriteria criteria) {
        Specification<CoursePlan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CoursePlan_.id));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildSpecification(criteria.getMonth(), CoursePlan_.month));
            }
            if (criteria.getTechnology() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTechnology(), CoursePlan_.technology));
            }
            if (criteria.getExtraPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExtraPrice(), CoursePlan_.extraPrice));
            }
        }
        return specification;
    }
}
