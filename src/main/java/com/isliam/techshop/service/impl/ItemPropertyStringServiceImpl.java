package com.isliam.techshop.service.impl;

import com.isliam.techshop.service.ItemPropertyStringService;
import com.isliam.techshop.domain.ItemPropertyString;
import com.isliam.techshop.repository.ItemPropertyStringRepository;
import com.isliam.techshop.service.dto.ItemPropertyStringDTO;
import com.isliam.techshop.service.mapper.ItemPropertyStringMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ItemPropertyString.
 */
@Service
@Transactional
public class ItemPropertyStringServiceImpl implements ItemPropertyStringService {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyStringServiceImpl.class);

    private final ItemPropertyStringRepository itemPropertyStringRepository;

    private final ItemPropertyStringMapper itemPropertyStringMapper;

    public ItemPropertyStringServiceImpl(ItemPropertyStringRepository itemPropertyStringRepository, ItemPropertyStringMapper itemPropertyStringMapper) {
        this.itemPropertyStringRepository = itemPropertyStringRepository;
        this.itemPropertyStringMapper = itemPropertyStringMapper;
    }

    /**
     * Save a itemPropertyString.
     *
     * @param itemPropertyStringDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ItemPropertyStringDTO save(ItemPropertyStringDTO itemPropertyStringDTO) {
        log.debug("Request to save ItemPropertyString : {}", itemPropertyStringDTO);
        ItemPropertyString itemPropertyString = itemPropertyStringMapper.toEntity(itemPropertyStringDTO);
        itemPropertyString = itemPropertyStringRepository.save(itemPropertyString);
        return itemPropertyStringMapper.toDto(itemPropertyString);
    }

    /**
     * Get all the itemPropertyStrings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ItemPropertyStringDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemPropertyStrings");
        return itemPropertyStringRepository.findAll(pageable)
            .map(itemPropertyStringMapper::toDto);
    }


    /**
     * Get one itemPropertyString by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemPropertyStringDTO> findOne(Long id) {
        log.debug("Request to get ItemPropertyString : {}", id);
        return itemPropertyStringRepository.findById(id)
            .map(itemPropertyStringMapper::toDto);
    }

    /**
     * Delete the itemPropertyString by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemPropertyString : {}", id);
        itemPropertyStringRepository.deleteById(id);
    }
}
