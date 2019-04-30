package com.isliam.techshop.service.mapper;

import com.isliam.techshop.domain.*;
import com.isliam.techshop.service.dto.PermissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Permission and its DTO PermissionDTO.
 */
@Mapper(componentModel = "spring", uses = {PositionMapper.class})
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {



    default Permission fromId(Long id) {
        if (id == null) {
            return null;
        }
        Permission permission = new Permission();
        permission.setId(id);
        return permission;
    }
}
