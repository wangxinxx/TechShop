package com.isliam.techshop.service;

import com.isliam.techshop.service.dto.ItemPropertyIntDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ItemPropertyInt.
 */
public interface ItemPropertyIntService {

    /**
     * Save a itemPropertyInt.
     *
     * @param itemPropertyIntDTO the entity to save
     * @return the persisted entity
     */
    ItemPropertyIntDTO save(ItemPropertyIntDTO itemPropertyIntDTO);

    /**
     * Get all the itemPropertyInts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ItemPropertyIntDTO> findAll(Pageable pageable);


    /**
     * Get the "id" itemPropertyInt.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ItemPropertyIntDTO> findOne(Long id);

    /**
     * Delete the "id" itemPropertyInt.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
