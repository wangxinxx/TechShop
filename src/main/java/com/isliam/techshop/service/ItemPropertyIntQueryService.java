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

import com.isliam.techshop.domain.ItemPropertyInt;
import com.isliam.techshop.domain.*; // for static metamodels
import com.isliam.techshop.repository.ItemPropertyIntRepository;
import com.isliam.techshop.service.dto.ItemPropertyIntCriteria;
import com.isliam.techshop.service.dto.ItemPropertyIntDTO;
import com.isliam.techshop.service.mapper.ItemPropertyIntMapper;

/**
 * Service for executing complex queries for ItemPropertyInt entities in the database.
 * The main input is a {@link ItemPropertyIntCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemPropertyIntDTO} or a {@link Page} of {@link ItemPropertyIntDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemPropertyIntQueryService extends QueryService<ItemPropertyInt> {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyIntQueryService.class);

    private final ItemPropertyIntRepository itemPropertyIntRepository;

    private final ItemPropertyIntMapper itemPropertyIntMapper;

    public ItemPropertyIntQueryService(ItemPropertyIntRepository itemPropertyIntRepository, ItemPropertyIntMapper itemPropertyIntMapper) {
        this.itemPropertyIntRepository = itemPropertyIntRepository;
        this.itemPropertyIntMapper = itemPropertyIntMapper;
    }

    /**
     * Return a {@link List} of {@link ItemPropertyIntDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemPropertyIntDTO> findByCriteria(ItemPropertyIntCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemPropertyInt> specification = createSpecification(criteria);
        return itemPropertyIntMapper.toDto(itemPropertyIntRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemPropertyIntDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPropertyIntDTO> findByCriteria(ItemPropertyIntCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemPropertyInt> specification = createSpecification(criteria);
        return itemPropertyIntRepository.findAll(specification, page)
            .map(itemPropertyIntMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemPropertyIntCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemPropertyInt> specification = createSpecification(criteria);
        return itemPropertyIntRepository.count(specification);
    }

    /**
     * Function to convert ItemPropertyIntCriteria to a {@link Specification}
     */
    private Specification<ItemPropertyInt> createSpecification(ItemPropertyIntCriteria criteria) {
        Specification<ItemPropertyInt> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ItemPropertyInt_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), ItemPropertyInt_.value));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(ItemPropertyInt_.item, JoinType.LEFT).get(Item_.id)));
            }
            if (criteria.getPropertyId() != null) {
                specification = specification.and(buildSpecification(criteria.getPropertyId(),
                    root -> root.join(ItemPropertyInt_.property, JoinType.LEFT).get(Property_.id)));
            }
        }
        return specification;
    }
}
