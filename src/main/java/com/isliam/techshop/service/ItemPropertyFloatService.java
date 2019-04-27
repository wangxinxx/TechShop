package com.isliam.techshop.service;

import com.isliam.techshop.service.dto.ItemPropertyFloatDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ItemPropertyFloat.
 */
public interface ItemPropertyFloatService {

    /**
     * Save a itemPropertyFloat.
     *
     * @param itemPropertyFloatDTO the entity to save
     * @return the persisted entity
     */
    ItemPropertyFloatDTO save(ItemPropertyFloatDTO itemPropertyFloatDTO);

    /**
     * Get all the itemPropertyFloats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ItemPropertyFloatDTO> findAll(Pageable pageable);


    /**
     * Get the "id" itemPropertyFloat.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ItemPropertyFloatDTO> findOne(Long id);

    /**
     * Delete the "id" itemPropertyFloat.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
