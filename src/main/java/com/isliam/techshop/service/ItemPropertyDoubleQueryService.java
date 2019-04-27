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

import com.isliam.techshop.domain.ItemPropertyDouble;
import com.isliam.techshop.domain.*; // for static metamodels
import com.isliam.techshop.repository.ItemPropertyDoubleRepository;
import com.isliam.techshop.service.dto.ItemPropertyDoubleCriteria;
import com.isliam.techshop.service.dto.ItemPropertyDoubleDTO;
import com.isliam.techshop.service.mapper.ItemPropertyDoubleMapper;

/**
 * Service for executing complex queries for ItemPropertyDouble entities in the database.
 * The main input is a {@link ItemPropertyDoubleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemPropertyDoubleDTO} or a {@link Page} of {@link ItemPropertyDoubleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemPropertyDoubleQueryService extends QueryService<ItemPropertyDouble> {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyDoubleQueryService.class);

    private final ItemPropertyDoubleRepository itemPropertyDoubleRepository;

    private final ItemPropertyDoubleMapper itemPropertyDoubleMapper;

    public ItemPropertyDoubleQueryService(ItemPropertyDoubleRepository itemPropertyDoubleRepository, ItemPropertyDoubleMapper itemPropertyDoubleMapper) {
        this.itemPropertyDoubleRepository = itemPropertyDoubleRepository;
        this.itemPropertyDoubleMapper = itemPropertyDoubleMapper;
    }

    /**
     * Return a {@link List} of {@link ItemPropertyDoubleDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemPropertyDoubleDTO> findByCriteria(ItemPropertyDoubleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemPropertyDouble> specification = createSpecification(criteria);
        return itemPropertyDoubleMapper.toDto(itemPropertyDoubleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemPropertyDoubleDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPropertyDoubleDTO> findByCriteria(ItemPropertyDoubleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemPropertyDouble> specification = createSpecification(criteria);
        return itemPropertyDoubleRepository.findAll(specification, page)
            .map(itemPropertyDoubleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemPropertyDoubleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemPropertyDouble> specification = createSpecification(criteria);
        return itemPropertyDoubleRepository.count(specification);
    }

    /**
     * Function to convert ItemPropertyDoubleCriteria to a {@link Specification}
     */
    private Specification<ItemPropertyDouble> createSpecification(ItemPropertyDoubleCriteria criteria) {
        Specification<ItemPropertyDouble> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ItemPropertyDouble_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), ItemPropertyDouble_.value));
            }
            if (criteria.getPropertyId() != null) {
                specification = specification.and(buildSpecification(criteria.getPropertyId(),
                    root -> root.join(ItemPropertyDouble_.property, JoinType.LEFT).get(Property_.id)));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(ItemPropertyDouble_.item, JoinType.LEFT).get(Item_.id)));
            }
        }
        return specification;
    }
}
