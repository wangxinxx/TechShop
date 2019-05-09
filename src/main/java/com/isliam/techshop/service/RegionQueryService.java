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

import com.isliam.techshop.domain.Region;
import com.isliam.techshop.domain.*; // for static metamodels
import com.isliam.techshop.repository.RegionRepository;
import com.isliam.techshop.service.dto.RegionCriteria;
import com.isliam.techshop.service.dto.RegionDTO;
import com.isliam.techshop.service.mapper.RegionMapper;

/**
 * Service for executing complex queries for Region entities in the database.
 * The main input is a {@link RegionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegionDTO} or a {@link Page} of {@link RegionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegionQueryService extends QueryService<Region> {

    private final Logger log = LoggerFactory.getLogger(RegionQueryService.class);

    private final RegionRepository regionRepository;

    private final RegionMapper regionMapper;

    public RegionQueryService(RegionRepository regionRepository, RegionMapper regionMapper) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
    }

    /**
     * Return a {@link List} of {@link RegionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegionDTO> findByCriteria(RegionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Region> specification = createSpecification(criteria);
        return regionMapper.toDto(regionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RegionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegionDTO> findByCriteria(RegionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Region> specification = createSpecification(criteria);
        return regionRepository.findAll(specification, page)
            .map(regionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Region> specification = createSpecification(criteria);
        return regionRepository.count(specification);
    }

    /**
     * Function to convert RegionCriteria to a {@link Specification}
     */
    private Specification<Region> createSpecification(RegionCriteria criteria) {
        Specification<Region> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Region_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Region_.name));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCountryId(),
                    root -> root.join(Region_.country, JoinType.LEFT).get(Country_.id)));
            }
        }
        return specification;
    }
}
