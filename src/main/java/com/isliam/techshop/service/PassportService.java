package com.isliam.techshop.service;

import com.isliam.techshop.service.dto.PassportDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Passport.
 */
public interface PassportService {

    /**
     * Save a passport.
     *
     * @param passportDTO the entity to save
     * @return the persisted entity
     */
    PassportDTO save(PassportDTO passportDTO);

    /**
     * Get all the passports.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PassportDTO> findAll(Pageable pageable);


    /**
     * Get the "id" passport.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PassportDTO> findOne(Long id);

    /**
     * Delete the "id" passport.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
