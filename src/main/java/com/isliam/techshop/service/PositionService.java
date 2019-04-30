package com.isliam.techshop.service;

import com.isliam.techshop.service.dto.PositionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Position.
 */
public interface PositionService {

    /**
     * Save a position.
     *
     * @param positionDTO the entity to save
     * @return the persisted entity
     */
    PositionDTO save(PositionDTO positionDTO);

    /**
     * Get all the positions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PositionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" position.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PositionDTO> findOne(Long id);

    /**
     * Delete the "id" position.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
