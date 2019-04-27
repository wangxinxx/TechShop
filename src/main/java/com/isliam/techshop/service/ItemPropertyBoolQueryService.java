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

import com.isliam.techshop.domain.ItemPropertyBool;
import com.isliam.techshop.domain.*; // for static metamodels
import com.isliam.techshop.repository.ItemPropertyBoolRepository;
import com.isliam.techshop.service.dto.ItemPropertyBoolCriteria;
import com.isliam.techshop.service.dto.ItemPropertyBoolDTO;
import com.isliam.techshop.service.mapper.ItemPropertyBoolMapper;

/**
 * Service for executing complex queries for ItemPropertyBool entities in the database.
 * The main input is a {@link ItemPropertyBoolCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemPropertyBoolDTO} or a {@link Page} of {@link ItemPropertyBoolDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemPropertyBoolQueryService extends QueryService<ItemPropertyBool> {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyBoolQueryService.class);

    private final ItemPropertyBoolRepository itemPropertyBoolRepository;

    private final ItemPropertyBoolMapper itemPropertyBoolMapper;

    public ItemPropertyBoolQueryService(ItemPropertyBoolRepository itemPropertyBoolRepository, ItemPropertyBoolMapper itemPropertyBoolMapper) {
        this.itemPropertyBoolRepository = itemPropertyBoolRepository;
        this.itemPropertyBoolMapper = itemPropertyBoolMapper;
    }

    /**
     * Return a {@link List} of {@link ItemPropertyBoolDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemPropertyBoolDTO> findByCriteria(ItemPropertyBoolCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemPropertyBool> specification = createSpecification(criteria);
        return itemPropertyBoolMapper.toDto(itemPropertyBoolRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemPropertyBoolDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPropertyBoolDTO> findByCriteria(ItemPropertyBoolCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemPropertyBool> specification = createSpecification(criteria);
        return itemPropertyBoolRepository.findAll(specification, page)
            .map(itemPropertyBoolMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemPropertyBoolCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemPropertyBool> specification = createSpecification(criteria);
        return itemPropertyBoolRepository.count(specification);
    }

    /**
     * Function to convert ItemPropertyBoolCriteria to a {@link Specification}
     */
    private Specification<ItemPropertyBool> createSpecification(ItemPropertyBoolCriteria criteria) {
        Specification<ItemPropertyBool> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ItemPropertyBool_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildSpecification(criteria.getValue(), ItemPropertyBool_.value));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(ItemPropertyBool_.item, JoinType.LEFT).get(Item_.id)));
            }
            if (criteria.getPropertyId() != null) {
                specification = specification.and(buildSpecification(criteria.getPropertyId(),
                    root -> root.join(ItemPropertyBool_.property, JoinType.LEFT).get(Property_.id)));
            }
        }
        return specification;
    }
}
