package com.isliam.techshop.service;

import com.isliam.techshop.service.dto.RegionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Region.
 */
public interface RegionService {

    /**
     * Save a region.
     *
     * @param regionDTO the entity to save
     * @return the persisted entity
     */
    RegionDTO save(RegionDTO regionDTO);

    /**
     * Get all the regions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RegionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" region.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RegionDTO> findOne(Long id);

    /**
     * Delete the "id" region.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
