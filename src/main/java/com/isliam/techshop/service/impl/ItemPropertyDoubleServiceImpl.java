package com.isliam.techshop.service.impl;

import com.isliam.techshop.service.ItemPropertyDoubleService;
import com.isliam.techshop.domain.ItemPropertyDouble;
import com.isliam.techshop.repository.ItemPropertyDoubleRepository;
import com.isliam.techshop.service.dto.ItemPropertyDoubleDTO;
import com.isliam.techshop.service.mapper.ItemPropertyDoubleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ItemPropertyDouble.
 */
@Service
@Transactional
public class ItemPropertyDoubleServiceImpl implements ItemPropertyDoubleService {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyDoubleServiceImpl.class);

    private final ItemPropertyDoubleRepository itemPropertyDoubleRepository;

    private final ItemPropertyDoubleMapper itemPropertyDoubleMapper;

    public ItemPropertyDoubleServiceImpl(ItemPropertyDoubleRepository itemPropertyDoubleRepository, ItemPropertyDoubleMapper itemPropertyDoubleMapper) {
        this.itemPropertyDoubleRepository = itemPropertyDoubleRepository;
        this.itemPropertyDoubleMapper = itemPropertyDoubleMapper;
    }

    /**
     * Save a itemPropertyDouble.
     *
     * @param itemPropertyDoubleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ItemPropertyDoubleDTO save(ItemPropertyDoubleDTO itemPropertyDoubleDTO) {
        log.debug("Request to save ItemPropertyDouble : {}", itemPropertyDoubleDTO);
        ItemPropertyDouble itemPropertyDouble = itemPropertyDoubleMapper.toEntity(itemPropertyDoubleDTO);
        itemPropertyDouble = itemPropertyDoubleRepository.save(itemPropertyDouble);
        return itemPropertyDoubleMapper.toDto(itemPropertyDouble);
    }

    /**
     * Get all the itemPropertyDoubles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ItemPropertyDoubleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemPropertyDoubles");
        return itemPropertyDoubleRepository.findAll(pageable)
            .map(itemPropertyDoubleMapper::toDto);
    }


    /**
     * Get one itemPropertyDouble by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemPropertyDoubleDTO> findOne(Long id) {
        log.debug("Request to get ItemPropertyDouble : {}", id);
        return itemPropertyDoubleRepository.findById(id)
            .map(itemPropertyDoubleMapper::toDto);
    }

    /**
     * Delete the itemPropertyDouble by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemPropertyDouble : {}", id);
        itemPropertyDoubleRepository.deleteById(id);
    }
}
