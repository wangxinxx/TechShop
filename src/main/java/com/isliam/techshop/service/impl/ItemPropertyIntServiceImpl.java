package com.isliam.techshop.service.impl;

import com.isliam.techshop.service.ItemPropertyIntService;
import com.isliam.techshop.domain.ItemPropertyInt;
import com.isliam.techshop.repository.ItemPropertyIntRepository;
import com.isliam.techshop.service.dto.ItemPropertyIntDTO;
import com.isliam.techshop.service.mapper.ItemPropertyIntMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ItemPropertyInt.
 */
@Service
@Transactional
public class ItemPropertyIntServiceImpl implements ItemPropertyIntService {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyIntServiceImpl.class);

    private final ItemPropertyIntRepository itemPropertyIntRepository;

    private final ItemPropertyIntMapper itemPropertyIntMapper;

    public ItemPropertyIntServiceImpl(ItemPropertyIntRepository itemPropertyIntRepository, ItemPropertyIntMapper itemPropertyIntMapper) {
        this.itemPropertyIntRepository = itemPropertyIntRepository;
        this.itemPropertyIntMapper = itemPropertyIntMapper;
    }

    /**
     * Save a itemPropertyInt.
     *
     * @param itemPropertyIntDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ItemPropertyIntDTO save(ItemPropertyIntDTO itemPropertyIntDTO) {
        log.debug("Request to save ItemPropertyInt : {}", itemPropertyIntDTO);
        ItemPropertyInt itemPropertyInt = itemPropertyIntMapper.toEntity(itemPropertyIntDTO);
        itemPropertyInt = itemPropertyIntRepository.save(itemPropertyInt);
        return itemPropertyIntMapper.toDto(itemPropertyInt);
    }

    /**
     * Get all the itemPropertyInts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ItemPropertyIntDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemPropertyInts");
        return itemPropertyIntRepository.findAll(pageable)
            .map(itemPropertyIntMapper::toDto);
    }


    /**
     * Get one itemPropertyInt by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemPropertyIntDTO> findOne(Long id) {
        log.debug("Request to get ItemPropertyInt : {}", id);
        return itemPropertyIntRepository.findById(id)
            .map(itemPropertyIntMapper::toDto);
    }

    /**
     * Delete the itemPropertyInt by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemPropertyInt : {}", id);
        itemPropertyIntRepository.deleteById(id);
    }
}
