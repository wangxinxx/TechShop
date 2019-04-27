package com.isliam.techshop.service.impl;

import com.isliam.techshop.service.ItemPropertyBoolService;
import com.isliam.techshop.domain.ItemPropertyBool;
import com.isliam.techshop.repository.ItemPropertyBoolRepository;
import com.isliam.techshop.service.dto.ItemPropertyBoolDTO;
import com.isliam.techshop.service.mapper.ItemPropertyBoolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ItemPropertyBool.
 */
@Service
@Transactional
public class ItemPropertyBoolServiceImpl implements ItemPropertyBoolService {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyBoolServiceImpl.class);

    private final ItemPropertyBoolRepository itemPropertyBoolRepository;

    private final ItemPropertyBoolMapper itemPropertyBoolMapper;

    public ItemPropertyBoolServiceImpl(ItemPropertyBoolRepository itemPropertyBoolRepository, ItemPropertyBoolMapper itemPropertyBoolMapper) {
        this.itemPropertyBoolRepository = itemPropertyBoolRepository;
        this.itemPropertyBoolMapper = itemPropertyBoolMapper;
    }

    /**
     * Save a itemPropertyBool.
     *
     * @param itemPropertyBoolDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ItemPropertyBoolDTO save(ItemPropertyBoolDTO itemPropertyBoolDTO) {
        log.debug("Request to save ItemPropertyBool : {}", itemPropertyBoolDTO);
        ItemPropertyBool itemPropertyBool = itemPropertyBoolMapper.toEntity(itemPropertyBoolDTO);
        itemPropertyBool = itemPropertyBoolRepository.save(itemPropertyBool);
        return itemPropertyBoolMapper.toDto(itemPropertyBool);
    }

    /**
     * Get all the itemPropertyBools.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ItemPropertyBoolDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemPropertyBools");
        return itemPropertyBoolRepository.findAll(pageable)
            .map(itemPropertyBoolMapper::toDto);
    }


    /**
     * Get one itemPropertyBool by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemPropertyBoolDTO> findOne(Long id) {
        log.debug("Request to get ItemPropertyBool : {}", id);
        return itemPropertyBoolRepository.findById(id)
            .map(itemPropertyBoolMapper::toDto);
    }

    /**
     * Delete the itemPropertyBool by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemPropertyBool : {}", id);
        itemPropertyBoolRepository.deleteById(id);
    }
}
