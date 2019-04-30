package com.isliam.techshop.service.impl;

import com.isliam.techshop.service.PositionService;
import com.isliam.techshop.domain.Position;
import com.isliam.techshop.repository.PositionRepository;
import com.isliam.techshop.service.dto.PositionDTO;
import com.isliam.techshop.service.mapper.PositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Position.
 */
@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    private final Logger log = LoggerFactory.getLogger(PositionServiceImpl.class);

    private final PositionRepository positionRepository;

    private final PositionMapper positionMapper;

    public PositionServiceImpl(PositionRepository positionRepository, PositionMapper positionMapper) {
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
    }

    /**
     * Save a position.
     *
     * @param positionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PositionDTO save(PositionDTO positionDTO) {
        log.debug("Request to save Position : {}", positionDTO);
        Position position = positionMapper.toEntity(positionDTO);
        position = positionRepository.save(position);
        return positionMapper.toDto(position);
    }

    /**
     * Get all the positions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PositionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Positions");
        return positionRepository.findAll(pageable)
            .map(positionMapper::toDto);
    }


    /**
     * Get one position by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PositionDTO> findOne(Long id) {
        log.debug("Request to get Position : {}", id);
        return positionRepository.findById(id)
            .map(positionMapper::toDto);
    }

    /**
     * Delete the position by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Position : {}", id);
        positionRepository.deleteById(id);
    }
}
