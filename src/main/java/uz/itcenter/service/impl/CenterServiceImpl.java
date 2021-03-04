package uz.itcenter.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.itcenter.domain.Center;
import uz.itcenter.repository.CenterRepository;
import uz.itcenter.service.CenterService;
import uz.itcenter.service.dto.CenterDTO;
import uz.itcenter.service.mapper.CenterMapper;

/**
 * Service Implementation for managing {@link Center}.
 */
@Service
@Transactional
public class CenterServiceImpl implements CenterService {
    private final Logger log = LoggerFactory.getLogger(CenterServiceImpl.class);

    private final CenterRepository centerRepository;

    private final CenterMapper centerMapper;

    public CenterServiceImpl(CenterRepository centerRepository, CenterMapper centerMapper) {
        this.centerRepository = centerRepository;
        this.centerMapper = centerMapper;
    }

    @Override
    public CenterDTO save(CenterDTO centerDTO) {
        log.debug("Request to save Center : {}", centerDTO);
        Center center = centerMapper.toEntity(centerDTO);
        center = centerRepository.save(center);
        return centerMapper.toDto(center);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CenterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Centers");
        return centerRepository.findAll(pageable).map(centerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CenterDTO> findOne(Long id) {
        log.debug("Request to get Center : {}", id);
        return centerRepository.findById(id).map(centerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Center : {}", id);
        centerRepository.deleteById(id);
    }

    @Override
    public Optional<CenterDTO> findAllowedCenter() {
        return this.centerRepository.findByManagerIsCurrentUser().map(centerMapper::toDto);
    }
}
