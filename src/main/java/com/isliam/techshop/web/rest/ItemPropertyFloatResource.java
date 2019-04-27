package com.isliam.techshop.web.rest;
import com.isliam.techshop.service.ItemPropertyFloatService;
import com.isliam.techshop.web.rest.errors.BadRequestAlertException;
import com.isliam.techshop.web.rest.util.HeaderUtil;
import com.isliam.techshop.web.rest.util.PaginationUtil;
import com.isliam.techshop.service.dto.ItemPropertyFloatDTO;
import com.isliam.techshop.service.dto.ItemPropertyFloatCriteria;
import com.isliam.techshop.service.ItemPropertyFloatQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ItemPropertyFloat.
 */
@RestController
@RequestMapping("/api")
public class ItemPropertyFloatResource {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyFloatResource.class);

    private static final String ENTITY_NAME = "itemPropertyFloat";

    private final ItemPropertyFloatService itemPropertyFloatService;

    private final ItemPropertyFloatQueryService itemPropertyFloatQueryService;

    public ItemPropertyFloatResource(ItemPropertyFloatService itemPropertyFloatService, ItemPropertyFloatQueryService itemPropertyFloatQueryService) {
        this.itemPropertyFloatService = itemPropertyFloatService;
        this.itemPropertyFloatQueryService = itemPropertyFloatQueryService;
    }

    /**
     * POST  /item-property-floats : Create a new itemPropertyFloat.
     *
     * @param itemPropertyFloatDTO the itemPropertyFloatDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemPropertyFloatDTO, or with status 400 (Bad Request) if the itemPropertyFloat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-property-floats")
    public ResponseEntity<ItemPropertyFloatDTO> createItemPropertyFloat(@Valid @RequestBody ItemPropertyFloatDTO itemPropertyFloatDTO) throws URISyntaxException {
        log.debug("REST request to save ItemPropertyFloat : {}", itemPropertyFloatDTO);
        if (itemPropertyFloatDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemPropertyFloat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPropertyFloatDTO result = itemPropertyFloatService.save(itemPropertyFloatDTO);
        return ResponseEntity.created(new URI("/api/item-property-floats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-property-floats : Updates an existing itemPropertyFloat.
     *
     * @param itemPropertyFloatDTO the itemPropertyFloatDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemPropertyFloatDTO,
     * or with status 400 (Bad Request) if the itemPropertyFloatDTO is not valid,
     * or with status 500 (Internal Server Error) if the itemPropertyFloatDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-property-floats")
    public ResponseEntity<ItemPropertyFloatDTO> updateItemPropertyFloat(@Valid @RequestBody ItemPropertyFloatDTO itemPropertyFloatDTO) throws URISyntaxException {
        log.debug("REST request to update ItemPropertyFloat : {}", itemPropertyFloatDTO);
        if (itemPropertyFloatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemPropertyFloatDTO result = itemPropertyFloatService.save(itemPropertyFloatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemPropertyFloatDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-property-floats : get all the itemPropertyFloats.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of itemPropertyFloats in body
     */
    @GetMapping("/item-property-floats")
    public ResponseEntity<List<ItemPropertyFloatDTO>> getAllItemPropertyFloats(ItemPropertyFloatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemPropertyFloats by criteria: {}", criteria);
        Page<ItemPropertyFloatDTO> page = itemPropertyFloatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/item-property-floats");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /item-property-floats/count : count all the itemPropertyFloats.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/item-property-floats/count")
    public ResponseEntity<Long> countItemPropertyFloats(ItemPropertyFloatCriteria criteria) {
        log.debug("REST request to count ItemPropertyFloats by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemPropertyFloatQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /item-property-floats/:id : get the "id" itemPropertyFloat.
     *
     * @param id the id of the itemPropertyFloatDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemPropertyFloatDTO, or with status 404 (Not Found)
     */
    @GetMapping("/item-property-floats/{id}")
    public ResponseEntity<ItemPropertyFloatDTO> getItemPropertyFloat(@PathVariable Long id) {
        log.debug("REST request to get ItemPropertyFloat : {}", id);
        Optional<ItemPropertyFloatDTO> itemPropertyFloatDTO = itemPropertyFloatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemPropertyFloatDTO);
    }

    /**
     * DELETE  /item-property-floats/:id : delete the "id" itemPropertyFloat.
     *
     * @param id the id of the itemPropertyFloatDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-property-floats/{id}")
    public ResponseEntity<Void> deleteItemPropertyFloat(@PathVariable Long id) {
        log.debug("REST request to delete ItemPropertyFloat : {}", id);
        itemPropertyFloatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
