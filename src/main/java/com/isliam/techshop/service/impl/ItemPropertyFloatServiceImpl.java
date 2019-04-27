package com.isliam.techshop.service.impl;

import com.isliam.techshop.service.ItemPropertyFloatService;
import com.isliam.techshop.domain.ItemPropertyFloat;
import com.isliam.techshop.repository.ItemPropertyFloatRepository;
import com.isliam.techshop.service.dto.ItemPropertyFloatDTO;
import com.isliam.techshop.service.mapper.ItemPropertyFloatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ItemPropertyFloat.
 */
@Service
@Transactional
public class ItemPropertyFloatServiceImpl implements ItemPropertyFloatService {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyFloatServiceImpl.class);

    private final ItemPropertyFloatRepository itemPropertyFloatRepository;

    private final ItemPropertyFloatMapper itemPropertyFloatMapper;

    public ItemPropertyFloatServiceImpl(ItemPropertyFloatRepository itemPropertyFloatRepository, ItemPropertyFloatMapper itemPropertyFloatMapper) {
        this.itemPropertyFloatRepository = itemPropertyFloatRepository;
        this.itemPropertyFloatMapper = itemPropertyFloatMapper;
    }

    /**
     * Save a itemPropertyFloat.
     *
     * @param itemPropertyFloatDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ItemPropertyFloatDTO save(ItemPropertyFloatDTO itemPropertyFloatDTO) {
        log.debug("Request to save ItemPropertyFloat : {}", itemPropertyFloatDTO);
        ItemPropertyFloat itemPropertyFloat = itemPropertyFloatMapper.toEntity(itemPropertyFloatDTO);
        itemPropertyFloat = itemPropertyFloatRepository.save(itemPropertyFloat);
        return itemPropertyFloatMapper.toDto(itemPropertyFloat);
    }

    /**
     * Get all the itemPropertyFloats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ItemPropertyFloatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemPropertyFloats");
        return itemPropertyFloatRepository.findAll(pageable)
            .map(itemPropertyFloatMapper::toDto);
    }


    /**
     * Get one itemPropertyFloat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemPropertyFloatDTO> findOne(Long id) {
        log.debug("Request to get ItemPropertyFloat : {}", id);
        return itemPropertyFloatRepository.findById(id)
            .map(itemPropertyFloatMapper::toDto);
    }

    /**
     * Delete the itemPropertyFloat by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemPropertyFloat : {}", id);
        itemPropertyFloatRepository.deleteById(id);
    }
}
