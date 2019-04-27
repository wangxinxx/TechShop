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

import com.isliam.techshop.domain.ItemPropertyFloat;
import com.isliam.techshop.domain.*; // for static metamodels
import com.isliam.techshop.repository.ItemPropertyFloatRepository;
import com.isliam.techshop.service.dto.ItemPropertyFloatCriteria;
import com.isliam.techshop.service.dto.ItemPropertyFloatDTO;
import com.isliam.techshop.service.mapper.ItemPropertyFloatMapper;

/**
 * Service for executing complex queries for ItemPropertyFloat entities in the database.
 * The main input is a {@link ItemPropertyFloatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemPropertyFloatDTO} or a {@link Page} of {@link ItemPropertyFloatDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemPropertyFloatQueryService extends QueryService<ItemPropertyFloat> {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyFloatQueryService.class);

    private final ItemPropertyFloatRepository itemPropertyFloatRepository;

    private final ItemPropertyFloatMapper itemPropertyFloatMapper;

    public ItemPropertyFloatQueryService(ItemPropertyFloatRepository itemPropertyFloatRepository, ItemPropertyFloatMapper itemPropertyFloatMapper) {
        this.itemPropertyFloatRepository = itemPropertyFloatRepository;
        this.itemPropertyFloatMapper = itemPropertyFloatMapper;
    }

    /**
     * Return a {@link List} of {@link ItemPropertyFloatDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemPropertyFloatDTO> findByCriteria(ItemPropertyFloatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemPropertyFloat> specification = createSpecification(criteria);
        return itemPropertyFloatMapper.toDto(itemPropertyFloatRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemPropertyFloatDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPropertyFloatDTO> findByCriteria(ItemPropertyFloatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemPropertyFloat> specification = createSpecification(criteria);
        return itemPropertyFloatRepository.findAll(specification, page)
            .map(itemPropertyFloatMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemPropertyFloatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemPropertyFloat> specification = createSpecification(criteria);
        return itemPropertyFloatRepository.count(specification);
    }

    /**
     * Function to convert ItemPropertyFloatCriteria to a {@link Specification}
     */
    private Specification<ItemPropertyFloat> createSpecification(ItemPropertyFloatCriteria criteria) {
        Specification<ItemPropertyFloat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ItemPropertyFloat_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), ItemPropertyFloat_.value));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(ItemPropertyFloat_.item, JoinType.LEFT).get(Item_.id)));
            }
            if (criteria.getPropertyId() != null) {
                specification = specification.and(buildSpecification(criteria.getPropertyId(),
                    root -> root.join(ItemPropertyFloat_.property, JoinType.LEFT).get(Property_.id)));
            }
        }
        return specification;
    }
}
