package com.isliam.techshop.service.impl;

import com.isliam.techshop.service.PermissionService;
import com.isliam.techshop.domain.Permission;
import com.isliam.techshop.repository.PermissionRepository;
import com.isliam.techshop.service.dto.PermissionDTO;
import com.isliam.techshop.service.mapper.PermissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Permission.
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);

    private final PermissionRepository permissionRepository;

    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    /**
     * Save a permission.
     *
     * @param permissionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PermissionDTO save(PermissionDTO permissionDTO) {
        log.debug("Request to save Permission : {}", permissionDTO);
        Permission permission = permissionMapper.toEntity(permissionDTO);
        permission = permissionRepository.save(permission);
        return permissionMapper.toDto(permission);
    }

    /**
     * Get all the permissions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PermissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Permissions");
        return permissionRepository.findAll(pageable)
            .map(permissionMapper::toDto);
    }

    /**
     * Get all the Permission with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<PermissionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return permissionRepository.findAllWithEagerRelationships(pageable).map(permissionMapper::toDto);
    }
    

    /**
     * Get one permission by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PermissionDTO> findOne(Long id) {
        log.debug("Request to get Permission : {}", id);
        return permissionRepository.findOneWithEagerRelationships(id)
            .map(permissionMapper::toDto);
    }

    /**
     * Delete the permission by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Permission : {}", id);
        permissionRepository.deleteById(id);
    }
}
