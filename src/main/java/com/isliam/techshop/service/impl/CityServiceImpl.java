package com.isliam.techshop.service.impl;

import com.isliam.techshop.service.CityService;
import com.isliam.techshop.domain.City;
import com.isliam.techshop.repository.CityRepository;
import com.isliam.techshop.service.dto.CityDTO;
import com.isliam.techshop.service.mapper.CityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing City.
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    /**
     * Save a city.
     *
     * @param cityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CityDTO save(CityDTO cityDTO) {
        log.debug("Request to save City : {}", cityDTO);
        City city = cityMapper.toEntity(cityDTO);
        city = cityRepository.save(city);
        return cityMapper.toDto(city);
    }

    /**
     * Get all the cities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cities");
        return cityRepository.findAll(pageable)
            .map(cityMapper::toDto);
    }


    /**
     * Get one city by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CityDTO> findOne(Long id) {
        log.debug("Request to get City : {}", id);
        return cityRepository.findById(id)
            .map(cityMapper::toDto);
    }

    /**
     * Delete the city by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete City : {}", id);
        cityRepository.deleteById(id);
    }
}
