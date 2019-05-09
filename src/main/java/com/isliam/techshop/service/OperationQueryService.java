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

import com.isliam.techshop.domain.Operation;
import com.isliam.techshop.domain.*; // for static metamodels
import com.isliam.techshop.repository.OperationRepository;
import com.isliam.techshop.service.dto.OperationCriteria;
import com.isliam.techshop.service.dto.OperationDTO;
import com.isliam.techshop.service.mapper.OperationMapper;

/**
 * Service for executing complex queries for Operation entities in the database.
 * The main input is a {@link OperationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OperationDTO} or a {@link Page} of {@link OperationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OperationQueryService extends QueryService<Operation> {

    private final Logger log = LoggerFactory.getLogger(OperationQueryService.class);

    private final OperationRepository operationRepository;

    private final OperationMapper operationMapper;

    public OperationQueryService(OperationRepository operationRepository, OperationMapper operationMapper) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
    }

    /**
     * Return a {@link List} of {@link OperationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OperationDTO> findByCriteria(OperationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Operation> specification = createSpecification(criteria);
        return operationMapper.toDto(operationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OperationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OperationDTO> findByCriteria(OperationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Operation> specification = createSpecification(criteria);
        return operationRepository.findAll(specification, page)
            .map(operationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OperationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Operation> specification = createSpecification(criteria);
        return operationRepository.count(specification);
    }

    /**
     * Function to convert OperationCriteria to a {@link Specification}
     */
    private Specification<Operation> createSpecification(OperationCriteria criteria) {
        Specification<Operation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Operation_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Operation_.type));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildSpecification(criteria.getState(), Operation_.state));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Operation_.description));
            }
            if (criteria.getOrderDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderDate(), Operation_.orderDate));
            }
            if (criteria.getApproveDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApproveDate(), Operation_.approveDate));
            }
            if (criteria.getDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryDate(), Operation_.deliveryDate));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(Operation_.customer, JoinType.LEFT).get(Profile_.id)));
            }
            if (criteria.getSellerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSellerId(),
                    root -> root.join(Operation_.seller, JoinType.LEFT).get(Profile_.id)));
            }
            if (criteria.getCurierId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurierId(),
                    root -> root.join(Operation_.curier, JoinType.LEFT).get(Profile_.id)));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(Operation_.item, JoinType.LEFT).get(Item_.id)));
            }
            if (criteria.getAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressId(),
                    root -> root.join(Operation_.address, JoinType.LEFT).get(Address_.id)));
            }
        }
        return specification;
    }
}
