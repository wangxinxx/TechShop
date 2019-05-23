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

import com.isliam.techshop.domain.Item;
import com.isliam.techshop.domain.*; // for static metamodels
import com.isliam.techshop.repository.ItemRepository;
import com.isliam.techshop.service.dto.ItemCriteria;
import com.isliam.techshop.service.dto.ItemDTO;
import com.isliam.techshop.service.mapper.ItemMapper;

/**
 * Service for executing complex queries for Item entities in the database.
 * The main input is a {@link ItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemDTO} or a {@link Page} of {@link ItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemQueryService extends QueryService<Item> {

    private final Logger log = LoggerFactory.getLogger(ItemQueryService.class);

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    public ItemQueryService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    /**
     * Return a {@link List} of {@link ItemDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemDTO> findByCriteria(ItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Item> specification = createSpecification(criteria);
        return itemMapper.toDto(itemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemDTO> findByCriteria(ItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Item> specification = createSpecification(criteria);
        return itemRepository.findAll(specification, page)
            .map(itemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Item> specification = createSpecification(criteria);
        return itemRepository.count(specification);
    }

    /**
     * Function to convert ItemCriteria to a {@link Specification}
     */
    private Specification<Item> createSpecification(ItemCriteria criteria) {
        Specification<Item> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Item_.id));
            }
            if (criteria.getGtin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGtin(), Item_.gtin));
            }
            if (criteria.getCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCost(), Item_.cost));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Item_.name));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Item_.active));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(Item_.product, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getManufacturerId() != null) {
                specification = specification.and(buildSpecification(criteria.getManufacturerId(),
                    root -> root.join(Item_.manufacturer, JoinType.LEFT).get(Manufacturer_.id)));
            }
            if (criteria.getItemPropertyBoolId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemPropertyBoolId(),
                    root -> root.join(Item_.itemPropertyBools, JoinType.LEFT).get(ItemPropertyBool_.id)));
            }
            if (criteria.getItemPropertyDoubleId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemPropertyDoubleId(),
                    root -> root.join(Item_.itemPropertyDoubles, JoinType.LEFT).get(ItemPropertyDouble_.id)));
            }
            if (criteria.getItemPropertyFloatId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemPropertyFloatId(),
                    root -> root.join(Item_.itemPropertyFloats, JoinType.LEFT).get(ItemPropertyFloat_.id)));
            }
            if (criteria.getItemPropertyIntId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemPropertyIntId(),
                    root -> root.join(Item_.itemPropertyInts, JoinType.LEFT).get(ItemPropertyInt_.id)));
            }
            if (criteria.getItemPropertyStringId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemPropertyStringId(),
                    root -> root.join(Item_.itemPropertyStrings, JoinType.LEFT).get(ItemPropertyString_.id)));
            }
        }
        return specification;
    }
}
