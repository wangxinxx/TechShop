package com.isliam.techshop.service;

import com.isliam.techshop.service.dto.ItemPropertyBoolDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ItemPropertyBool.
 */
public interface ItemPropertyBoolService {

    /**
     * Save a itemPropertyBool.
     *
     * @param itemPropertyBoolDTO the entity to save
     * @return the persisted entity
     */
    ItemPropertyBoolDTO save(ItemPropertyBoolDTO itemPropertyBoolDTO);

    /**
     * Get all the itemPropertyBools.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ItemPropertyBoolDTO> findAll(Pageable pageable);


    /**
     * Get the "id" itemPropertyBool.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ItemPropertyBoolDTO> findOne(Long id);

    /**
     * Delete the "id" itemPropertyBool.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
