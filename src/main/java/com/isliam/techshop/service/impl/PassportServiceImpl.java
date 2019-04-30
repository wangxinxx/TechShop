package com.isliam.techshop.service.impl;

import com.isliam.techshop.service.PassportService;
import com.isliam.techshop.domain.Passport;
import com.isliam.techshop.repository.PassportRepository;
import com.isliam.techshop.service.dto.PassportDTO;
import com.isliam.techshop.service.mapper.PassportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Passport.
 */
@Service
@Transactional
public class PassportServiceImpl implements PassportService {

    private final Logger log = LoggerFactory.getLogger(PassportServiceImpl.class);

    private final PassportRepository passportRepository;

    private final PassportMapper passportMapper;

    public PassportServiceImpl(PassportRepository passportRepository, PassportMapper passportMapper) {
        this.passportRepository = passportRepository;
        this.passportMapper = passportMapper;
    }

    /**
     * Save a passport.
     *
     * @param passportDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PassportDTO save(PassportDTO passportDTO) {
        log.debug("Request to save Passport : {}", passportDTO);
        Passport passport = passportMapper.toEntity(passportDTO);
        passport = passportRepository.save(passport);
        return passportMapper.toDto(passport);
    }

    /**
     * Get all the passports.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PassportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Passports");
        return passportRepository.findAll(pageable)
            .map(passportMapper::toDto);
    }


    /**
     * Get one passport by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PassportDTO> findOne(Long id) {
        log.debug("Request to get Passport : {}", id);
        return passportRepository.findById(id)
            .map(passportMapper::toDto);
    }

    /**
     * Delete the passport by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Passport : {}", id);
        passportRepository.deleteById(id);
    }
}
