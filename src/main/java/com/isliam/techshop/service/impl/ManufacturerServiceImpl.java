package com.isliam.techshop.service.impl;

import com.isliam.techshop.service.ManufacturerService;
import com.isliam.techshop.domain.Manufacturer;
import com.isliam.techshop.repository.ManufacturerRepository;
import com.isliam.techshop.service.dto.ManufacturerDTO;
import com.isliam.techshop.service.mapper.ManufacturerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Manufacturer.
 */
@Service
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService {

    private final Logger log = LoggerFactory.getLogger(ManufacturerServiceImpl.class);

    private final ManufacturerRepository manufacturerRepository;

    private final ManufacturerMapper manufacturerMapper;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository, ManufacturerMapper manufacturerMapper) {
        this.manufacturerRepository = manufacturerRepository;
        this.manufacturerMapper = manufacturerMapper;
    }

    /**
     * Save a manufacturer.
     *
     * @param manufacturerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ManufacturerDTO save(ManufacturerDTO manufacturerDTO) {
        log.debug("Request to save Manufacturer : {}", manufacturerDTO);
        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerDTO);
        manufacturer = manufacturerRepository.save(manufacturer);
        return manufacturerMapper.toDto(manufacturer);
    }

    /**
     * Get all the manufacturers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ManufacturerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Manufacturers");
        return manufacturerRepository.findAll(pageable)
            .map(manufacturerMapper::toDto);
    }


    /**
     * Get one manufacturer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ManufacturerDTO> findOne(Long id) {
        log.debug("Request to get Manufacturer : {}", id);
        return manufacturerRepository.findById(id)
            .map(manufacturerMapper::toDto);
    }

    /**
     * Delete the manufacturer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Manufacturer : {}", id);
        manufacturerRepository.deleteById(id);
    }
}
