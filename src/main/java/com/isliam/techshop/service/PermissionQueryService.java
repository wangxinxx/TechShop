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

import com.isliam.techshop.domain.Permission;
import com.isliam.techshop.domain.*; // for static metamodels
import com.isliam.techshop.repository.PermissionRepository;
import com.isliam.techshop.service.dto.PermissionCriteria;
import com.isliam.techshop.service.dto.PermissionDTO;
import com.isliam.techshop.service.mapper.PermissionMapper;

/**
 * Service for executing complex queries for Permission entities in the database.
 * The main input is a {@link PermissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PermissionDTO} or a {@link Page} of {@link PermissionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PermissionQueryService extends QueryService<Permission> {

    private final Logger log = LoggerFactory.getLogger(PermissionQueryService.class);

    private final PermissionRepository permissionRepository;

    private final PermissionMapper permissionMapper;

    public PermissionQueryService(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    /**
     * Return a {@link List} of {@link PermissionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PermissionDTO> findByCriteria(PermissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Permission> specification = createSpecification(criteria);
        return permissionMapper.toDto(permissionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PermissionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PermissionDTO> findByCriteria(PermissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Permission> specification = createSpecification(criteria);
        return permissionRepository.findAll(specification, page)
            .map(permissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PermissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Permission> specification = createSpecification(criteria);
        return permissionRepository.count(specification);
    }

    /**
     * Function to convert PermissionCriteria to a {@link Specification}
     */
    private Specification<Permission> createSpecification(PermissionCriteria criteria) {
        Specification<Permission> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Permission_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Permission_.name));
            }
        }
        return specification;
    }
}
