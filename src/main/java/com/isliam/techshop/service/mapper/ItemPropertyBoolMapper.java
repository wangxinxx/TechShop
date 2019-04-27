package com.isliam.techshop.service.mapper;

import com.isliam.techshop.domain.*;
import com.isliam.techshop.service.dto.ItemPropertyBoolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ItemPropertyBool and its DTO ItemPropertyBoolDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemMapper.class, PropertyMapper.class})
public interface ItemPropertyBoolMapper extends EntityMapper<ItemPropertyBoolDTO, ItemPropertyBool> {

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "property.name", target = "propertyName")
    ItemPropertyBoolDTO toDto(ItemPropertyBool itemPropertyBool);

    @Mapping(source = "itemId", target = "item")
    @Mapping(source = "propertyId", target = "property")
    ItemPropertyBool toEntity(ItemPropertyBoolDTO itemPropertyBoolDTO);

    default ItemPropertyBool fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemPropertyBool itemPropertyBool = new ItemPropertyBool();
        itemPropertyBool.setId(id);
        return itemPropertyBool;
    }
}
