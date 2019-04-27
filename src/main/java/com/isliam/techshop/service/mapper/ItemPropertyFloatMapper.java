package com.isliam.techshop.service.mapper;

import com.isliam.techshop.domain.*;
import com.isliam.techshop.service.dto.ItemPropertyFloatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ItemPropertyFloat and its DTO ItemPropertyFloatDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemMapper.class, PropertyMapper.class})
public interface ItemPropertyFloatMapper extends EntityMapper<ItemPropertyFloatDTO, ItemPropertyFloat> {

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "property.name", target = "propertyName")
    ItemPropertyFloatDTO toDto(ItemPropertyFloat itemPropertyFloat);

    @Mapping(source = "itemId", target = "item")
    @Mapping(source = "propertyId", target = "property")
    ItemPropertyFloat toEntity(ItemPropertyFloatDTO itemPropertyFloatDTO);

    default ItemPropertyFloat fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemPropertyFloat itemPropertyFloat = new ItemPropertyFloat();
        itemPropertyFloat.setId(id);
        return itemPropertyFloat;
    }
}
