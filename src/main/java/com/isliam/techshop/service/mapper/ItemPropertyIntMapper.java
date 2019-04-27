package com.isliam.techshop.service.mapper;

import com.isliam.techshop.domain.*;
import com.isliam.techshop.service.dto.ItemPropertyIntDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ItemPropertyInt and its DTO ItemPropertyIntDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemMapper.class, PropertyMapper.class})
public interface ItemPropertyIntMapper extends EntityMapper<ItemPropertyIntDTO, ItemPropertyInt> {

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "property.name", target = "propertyName")
    ItemPropertyIntDTO toDto(ItemPropertyInt itemPropertyInt);

    @Mapping(source = "itemId", target = "item")
    @Mapping(source = "propertyId", target = "property")
    ItemPropertyInt toEntity(ItemPropertyIntDTO itemPropertyIntDTO);

    default ItemPropertyInt fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemPropertyInt itemPropertyInt = new ItemPropertyInt();
        itemPropertyInt.setId(id);
        return itemPropertyInt;
    }
}
