package com.isliam.techshop.service.mapper;

import com.isliam.techshop.domain.*;
import com.isliam.techshop.service.dto.ItemPropertyStringDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ItemPropertyString and its DTO ItemPropertyStringDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemMapper.class, PropertyMapper.class})
public interface ItemPropertyStringMapper extends EntityMapper<ItemPropertyStringDTO, ItemPropertyString> {

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "property.name", target = "propertyName")
    ItemPropertyStringDTO toDto(ItemPropertyString itemPropertyString);

    @Mapping(source = "itemId", target = "item")
    @Mapping(source = "propertyId", target = "property")
    ItemPropertyString toEntity(ItemPropertyStringDTO itemPropertyStringDTO);

    default ItemPropertyString fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemPropertyString itemPropertyString = new ItemPropertyString();
        itemPropertyString.setId(id);
        return itemPropertyString;
    }
}
