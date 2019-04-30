package com.isliam.techshop.service.mapper;

import com.isliam.techshop.domain.*;
import com.isliam.techshop.service.dto.PositionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Position and its DTO PositionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PositionMapper extends EntityMapper<PositionDTO, Position> {

    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "manager.name", target = "managerName")
    PositionDTO toDto(Position position);

    @Mapping(source = "managerId", target = "manager")
    Position toEntity(PositionDTO positionDTO);

    default Position fromId(Long id) {
        if (id == null) {
            return null;
        }
        Position position = new Position();
        position.setId(id);
        return position;
    }
}
