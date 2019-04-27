package com.isliam.techshop.service;

import com.isliam.techshop.service.dto.ItemPropertyDoubleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ItemPropertyDouble.
 */
public interface ItemPropertyDoubleService {

    /**
     * Save a itemPropertyDouble.
     *
     * @param itemPropertyDoubleDTO the entity to save
     * @return the persisted entity
     */
    ItemPropertyDoubleDTO save(ItemPropertyDoubleDTO itemPropertyDoubleDTO);

    /**
     * Get all the itemPropertyDoubles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ItemPropertyDoubleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" itemPropertyDouble.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ItemPropertyDoubleDTO> findOne(Long id);

    /**
     * Delete the "id" itemPropertyDouble.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
