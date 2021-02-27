package uz.itcenter.service.impl;

import uz.itcenter.service.CoursePlanService;
import uz.itcenter.domain.CoursePlan;
import uz.itcenter.repository.CoursePlanRepository;
import uz.itcenter.service.dto.CoursePlanDTO;
import uz.itcenter.service.mapper.CoursePlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CoursePlan}.
 */
@Service
@Transactional
public class CoursePlanServiceImpl implements CoursePlanService {

    private final Logger log = LoggerFactory.getLogger(CoursePlanServiceImpl.class);

    private final CoursePlanRepository coursePlanRepository;

    private final CoursePlanMapper coursePlanMapper;

    public CoursePlanServiceImpl(CoursePlanRepository coursePlanRepository, CoursePlanMapper coursePlanMapper) {
        this.coursePlanRepository = coursePlanRepository;
        this.coursePlanMapper = coursePlanMapper;
    }

    @Override
    public CoursePlanDTO save(CoursePlanDTO coursePlanDTO) {
        log.debug("Request to save CoursePlan : {}", coursePlanDTO);
        CoursePlan coursePlan = coursePlanMapper.toEntity(coursePlanDTO);
        coursePlan = coursePlanRepository.save(coursePlan);
        return coursePlanMapper.toDto(coursePlan);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CoursePlanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CoursePlans");
        return coursePlanRepository.findAll(pageable)
            .map(coursePlanMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CoursePlanDTO> findOne(Long id) {
        log.debug("Request to get CoursePlan : {}", id);
        return coursePlanRepository.findById(id)
            .map(coursePlanMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CoursePlan : {}", id);
        coursePlanRepository.deleteById(id);
    }
}
