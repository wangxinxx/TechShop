package com.isliam.techshop.service.mapper;

import com.isliam.techshop.domain.*;
import com.isliam.techshop.service.dto.ItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Item and its DTO ItemDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, ManufacturerMapper.class})
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "manufacturer.id", target = "manufacturerId")
    @Mapping(source = "manufacturer.name", target = "manufacturerName")
    ItemDTO toDto(Item item);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "manufacturerId", target = "manufacturer")
    @Mapping(target = "itemPropertyBools", ignore = true)
    @Mapping(target = "itemPropertyDoubles", ignore = true)
    @Mapping(target = "itemPropertyFloats", ignore = true)
    @Mapping(target = "itemPropertyInts", ignore = true)
    @Mapping(target = "itemPropertyStrings", ignore = true)
    Item toEntity(ItemDTO itemDTO);

    default Item fromId(Long id) {
        if (id == null) {
            return null;
        }
        Item item = new Item();
        item.setId(id);
        return item;
    }
}
