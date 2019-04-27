package com.isliam.techshop.service.mapper;

import com.isliam.techshop.domain.*;
import com.isliam.techshop.service.dto.ItemPropertyDoubleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ItemPropertyDouble and its DTO ItemPropertyDoubleDTO.
 */
@Mapper(componentModel = "spring", uses = {PropertyMapper.class, ItemMapper.class})
public interface ItemPropertyDoubleMapper extends EntityMapper<ItemPropertyDoubleDTO, ItemPropertyDouble> {

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "property.name", target = "propertyName")
    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    ItemPropertyDoubleDTO toDto(ItemPropertyDouble itemPropertyDouble);

    @Mapping(source = "propertyId", target = "property")
    @Mapping(source = "itemId", target = "item")
    ItemPropertyDouble toEntity(ItemPropertyDoubleDTO itemPropertyDoubleDTO);

    default ItemPropertyDouble fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemPropertyDouble itemPropertyDouble = new ItemPropertyDouble();
        itemPropertyDouble.setId(id);
        return itemPropertyDouble;
    }
}
