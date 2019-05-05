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

import com.isliam.techshop.domain.Notification;
import com.isliam.techshop.domain.*; // for static metamodels
import com.isliam.techshop.repository.NotificationRepository;
import com.isliam.techshop.service.dto.NotificationCriteria;
import com.isliam.techshop.service.dto.NotificationDTO;
import com.isliam.techshop.service.mapper.NotificationMapper;

/**
 * Service for executing complex queries for Notification entities in the database.
 * The main input is a {@link NotificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotificationDTO} or a {@link Page} of {@link NotificationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotificationQueryService extends QueryService<Notification> {

    private final Logger log = LoggerFactory.getLogger(NotificationQueryService.class);

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    public NotificationQueryService(NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

    /**
     * Return a {@link List} of {@link NotificationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotificationDTO> findByCriteria(NotificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Notification> specification = createSpecification(criteria);
        return notificationMapper.toDto(notificationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NotificationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationDTO> findByCriteria(NotificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Notification> specification = createSpecification(criteria);
        return notificationRepository.findAll(specification, page)
            .map(notificationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Notification> specification = createSpecification(criteria);
        return notificationRepository.count(specification);
    }

    /**
     * Function to convert NotificationCriteria to a {@link Specification}
     */
    private Specification<Notification> createSpecification(NotificationCriteria criteria) {
        Specification<Notification> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Notification_.id));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), Notification_.message));
            }
            if (criteria.getViewed() != null) {
                specification = specification.and(buildSpecification(criteria.getViewed(), Notification_.viewed));
            }
            if (criteria.getProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfileId(),
                    root -> root.join(Notification_.profile, JoinType.LEFT).get(Profile_.id)));
            }
        }
        return specification;
    }
}
