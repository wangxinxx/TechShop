package com.isliam.techshop.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.isliam.techshop.domain.Passport;
import com.isliam.techshop.domain.*; // for static metamodels
import com.isliam.techshop.repository.PassportRepository;
import com.isliam.techshop.service.dto.PassportCriteria;
import com.isliam.techshop.service.dto.PassportDTO;
import com.isliam.techshop.service.mapper.PassportMapper;

/**
 * Service for executing complex queries for Passport entities in the database.
 * The main input is a {@link PassportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PassportDTO} or a {@link Page} of {@link PassportDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PassportQueryService extends QueryService<Passport> {

    private final Logger log = LoggerFactory.getLogger(PassportQueryService.class);

    private final PassportRepository passportRepository;

    private final PassportMapper passportMapper;

    public PassportQueryService(PassportRepository passportRepository, PassportMapper passportMapper) {
        this.passportRepository = passportRepository;
        this.passportMapper = passportMapper;
    }

    /**
     * Return a {@link List} of {@link PassportDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PassportDTO> findByCriteria(PassportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Passport> specification = createSpecification(criteria);
        return passportMapper.toDto(passportRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PassportDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PassportDTO> findByCriteria(PassportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Passport> specification = createSpecification(criteria);
        return passportRepository.findAll(specification, page)
            .map(passportMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PassportCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Passport> specification = createSpecification(criteria);
        return passportRepository.count(specification);
    }

    /**
     * Function to convert PassportCriteria to a {@link Specification}
     */
    private Specification<Passport> createSpecification(PassportCriteria criteria) {
        Specification<Passport> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Passport_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Passport_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Passport_.lastName));
            }
            if (criteria.getPatronymic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatronymic(), Passport_.patronymic));
            }
            if (criteria.getDob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDob(), Passport_.dob));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), Passport_.serialNumber));
            }
            if (criteria.getTaxId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxId(), Passport_.taxId));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Passport_.active));
            }
        }
        return specification;
    }
}
