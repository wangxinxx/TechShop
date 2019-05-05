package com.isliam.techshop.service.mapper;

import com.isliam.techshop.domain.*;
import com.isliam.techshop.service.dto.OperationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Operation and its DTO OperationDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfileMapper.class, ItemMapper.class})
public interface OperationMapper extends EntityMapper<OperationDTO, Operation> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "seller.id", target = "sellerId")
    @Mapping(source = "curier.id", target = "curierId")
    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    OperationDTO toDto(Operation operation);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "sellerId", target = "seller")
    @Mapping(source = "curierId", target = "curier")
    @Mapping(source = "itemId", target = "item")
    Operation toEntity(OperationDTO operationDTO);

    default Operation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Operation operation = new Operation();
        operation.setId(id);
        return operation;
    }
}
