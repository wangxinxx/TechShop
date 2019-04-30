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

import com.isliam.techshop.domain.Position;
import com.isliam.techshop.domain.*; // for static metamodels
import com.isliam.techshop.repository.PositionRepository;
import com.isliam.techshop.service.dto.PositionCriteria;
import com.isliam.techshop.service.dto.PositionDTO;
import com.isliam.techshop.service.mapper.PositionMapper;

/**
 * Service for executing complex queries for Position entities in the database.
 * The main input is a {@link PositionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PositionDTO} or a {@link Page} of {@link PositionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PositionQueryService extends QueryService<Position> {

    private final Logger log = LoggerFactory.getLogger(PositionQueryService.class);

    private final PositionRepository positionRepository;

    private final PositionMapper positionMapper;

    public PositionQueryService(PositionRepository positionRepository, PositionMapper positionMapper) {
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
    }

    /**
     * Return a {@link List} of {@link PositionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PositionDTO> findByCriteria(PositionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Position> specification = createSpecification(criteria);
        return positionMapper.toDto(positionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PositionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PositionDTO> findByCriteria(PositionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Position> specification = createSpecification(criteria);
        return positionRepository.findAll(specification, page)
            .map(positionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PositionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Position> specification = createSpecification(criteria);
        return positionRepository.count(specification);
    }

    /**
     * Function to convert PositionCriteria to a {@link Specification}
     */
    private Specification<Position> createSpecification(PositionCriteria criteria) {
        Specification<Position> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Position_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Position_.name));
            }
            if (criteria.getManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getManagerId(),
                    root -> root.join(Position_.manager, JoinType.LEFT).get(Position_.id)));
            }
            if (criteria.getPermissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getPermissionId(),
                    root -> root.join(Position_.permissions, JoinType.LEFT).get(Permission_.id)));
            }
        }
        return specification;
    }
}
