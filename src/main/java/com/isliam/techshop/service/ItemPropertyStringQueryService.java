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

import com.isliam.techshop.domain.ItemPropertyString;
import com.isliam.techshop.domain.*; // for static metamodels
import com.isliam.techshop.repository.ItemPropertyStringRepository;
import com.isliam.techshop.service.dto.ItemPropertyStringCriteria;
import com.isliam.techshop.service.dto.ItemPropertyStringDTO;
import com.isliam.techshop.service.mapper.ItemPropertyStringMapper;

/**
 * Service for executing complex queries for ItemPropertyString entities in the database.
 * The main input is a {@link ItemPropertyStringCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemPropertyStringDTO} or a {@link Page} of {@link ItemPropertyStringDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemPropertyStringQueryService extends QueryService<ItemPropertyString> {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyStringQueryService.class);

    private final ItemPropertyStringRepository itemPropertyStringRepository;

    private final ItemPropertyStringMapper itemPropertyStringMapper;

    public ItemPropertyStringQueryService(ItemPropertyStringRepository itemPropertyStringRepository, ItemPropertyStringMapper itemPropertyStringMapper) {
        this.itemPropertyStringRepository = itemPropertyStringRepository;
        this.itemPropertyStringMapper = itemPropertyStringMapper;
    }

    /**
     * Return a {@link List} of {@link ItemPropertyStringDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemPropertyStringDTO> findByCriteria(ItemPropertyStringCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemPropertyString> specification = createSpecification(criteria);
        return itemPropertyStringMapper.toDto(itemPropertyStringRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemPropertyStringDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPropertyStringDTO> findByCriteria(ItemPropertyStringCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemPropertyString> specification = createSpecification(criteria);
        return itemPropertyStringRepository.findAll(specification, page)
            .map(itemPropertyStringMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemPropertyStringCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemPropertyString> specification = createSpecification(criteria);
        return itemPropertyStringRepository.count(specification);
    }

    /**
     * Function to convert ItemPropertyStringCriteria to a {@link Specification}
     */
    private Specification<ItemPropertyString> createSpecification(ItemPropertyStringCriteria criteria) {
        Specification<ItemPropertyString> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ItemPropertyString_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), ItemPropertyString_.value));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(ItemPropertyString_.item, JoinType.LEFT).get(Item_.id)));
            }
            if (criteria.getPropertyId() != null) {
                specification = specification.and(buildSpecification(criteria.getPropertyId(),
                    root -> root.join(ItemPropertyString_.property, JoinType.LEFT).get(Property_.id)));
            }
        }
        return specification;
    }
}
