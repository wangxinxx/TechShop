package com.isliam.techshop.service;

import com.isliam.techshop.service.dto.ItemPropertyStringDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ItemPropertyString.
 */
public interface ItemPropertyStringService {

    /**
     * Save a itemPropertyString.
     *
     * @param itemPropertyStringDTO the entity to save
     * @return the persisted entity
     */
    ItemPropertyStringDTO save(ItemPropertyStringDTO itemPropertyStringDTO);

    /**
     * Get all the itemPropertyStrings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ItemPropertyStringDTO> findAll(Pageable pageable);


    /**
     * Get the "id" itemPropertyString.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ItemPropertyStringDTO> findOne(Long id);

    /**
     * Delete the "id" itemPropertyString.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
