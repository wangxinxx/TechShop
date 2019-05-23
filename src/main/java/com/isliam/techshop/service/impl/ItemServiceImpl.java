package com.isliam.techshop.service.impl;

import com.isliam.techshop.domain.*;
import com.isliam.techshop.service.ItemService;
import com.isliam.techshop.repository.ItemRepository;
import com.isliam.techshop.service.dto.ItemDTO;
import com.isliam.techshop.service.mapper.ItemMapper;
import com.isliam.techshop.web.rest.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Item.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    /**
     * Save a item.
     *
     * @param itemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ItemDTO save(ItemDTO itemDTO) {
        log.debug("Request to save Item : {}", itemDTO);
        Item item = itemMapper.toEntity(itemDTO);
        item = itemRepository.save(item);
        return itemMapper.toDto(item);
    }

    /**
     * Get all the items.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Items");
        return itemRepository.findAll(pageable)
            .map(itemMapper::toDto);
    }


    /**
     * Get one item by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemDTO> findOne(Long id) {
        log.debug("Request to get Item : {}", id);
        Item item = itemRepository.findById(id)
            .orElseThrow(ResourceNotFoundException::new);

        ItemDTO itemDTO = itemMapper.toDto(item);
        itemDTO.setProperties(getProperties(item));

        return
            Optional.of(itemDTO);
    }

    private Map<String,String> getProperties(Item item){
        Set<ItemPropertyBool> itemPropertyBoolList = item.getItemPropertyBools();
        Set<ItemPropertyDouble> itemPropertyDoubles = item.getItemPropertyDoubles();
        Set<ItemPropertyFloat> itemPropertyFloats = item.getItemPropertyFloats();
        Set<ItemPropertyInt> itemPropertyInts = item.getItemPropertyInts();
        Set<ItemPropertyString> itemPropertyStrings = item.getItemPropertyStrings();

        Map<String, String> result = new HashMap<>();

        result.putAll(itemPropertyBoolList.stream().collect(
            Collectors.toMap( a->a.getProperty().getName(),a->a.isValue().toString())));

        result.putAll(itemPropertyDoubles.stream().collect(
            Collectors.toMap( a->a.getProperty().getName(),a->a.getValue().toString())));

        result.putAll(itemPropertyFloats.stream().collect(
            Collectors.toMap( a->a.getProperty().getName(),a->a.getValue().toString())));

        result.putAll(itemPropertyInts.stream().collect(
            Collectors.toMap( a->a.getProperty().getName(),a->a.getValue().toString())));

        result.putAll(itemPropertyStrings.stream().collect(
            Collectors.toMap( a->a.getProperty().getName(), ItemPropertyString::getValue)));

        return result;
    }

    /**
     * Delete the item by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.deleteById(id);
    }
}
