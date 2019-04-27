package com.isliam.techshop.web.rest;
import com.isliam.techshop.service.ItemPropertyStringService;
import com.isliam.techshop.web.rest.errors.BadRequestAlertException;
import com.isliam.techshop.web.rest.util.HeaderUtil;
import com.isliam.techshop.web.rest.util.PaginationUtil;
import com.isliam.techshop.service.dto.ItemPropertyStringDTO;
import com.isliam.techshop.service.dto.ItemPropertyStringCriteria;
import com.isliam.techshop.service.ItemPropertyStringQueryService;
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
 * REST controller for managing ItemPropertyString.
 */
@RestController
@RequestMapping("/api")
public class ItemPropertyStringResource {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyStringResource.class);

    private static final String ENTITY_NAME = "itemPropertyString";

    private final ItemPropertyStringService itemPropertyStringService;

    private final ItemPropertyStringQueryService itemPropertyStringQueryService;

    public ItemPropertyStringResource(ItemPropertyStringService itemPropertyStringService, ItemPropertyStringQueryService itemPropertyStringQueryService) {
        this.itemPropertyStringService = itemPropertyStringService;
        this.itemPropertyStringQueryService = itemPropertyStringQueryService;
    }

    /**
     * POST  /item-property-strings : Create a new itemPropertyString.
     *
     * @param itemPropertyStringDTO the itemPropertyStringDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemPropertyStringDTO, or with status 400 (Bad Request) if the itemPropertyString has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-property-strings")
    public ResponseEntity<ItemPropertyStringDTO> createItemPropertyString(@Valid @RequestBody ItemPropertyStringDTO itemPropertyStringDTO) throws URISyntaxException {
        log.debug("REST request to save ItemPropertyString : {}", itemPropertyStringDTO);
        if (itemPropertyStringDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemPropertyString cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPropertyStringDTO result = itemPropertyStringService.save(itemPropertyStringDTO);
        return ResponseEntity.created(new URI("/api/item-property-strings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-property-strings : Updates an existing itemPropertyString.
     *
     * @param itemPropertyStringDTO the itemPropertyStringDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemPropertyStringDTO,
     * or with status 400 (Bad Request) if the itemPropertyStringDTO is not valid,
     * or with status 500 (Internal Server Error) if the itemPropertyStringDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-property-strings")
    public ResponseEntity<ItemPropertyStringDTO> updateItemPropertyString(@Valid @RequestBody ItemPropertyStringDTO itemPropertyStringDTO) throws URISyntaxException {
        log.debug("REST request to update ItemPropertyString : {}", itemPropertyStringDTO);
        if (itemPropertyStringDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemPropertyStringDTO result = itemPropertyStringService.save(itemPropertyStringDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemPropertyStringDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-property-strings : get all the itemPropertyStrings.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of itemPropertyStrings in body
     */
    @GetMapping("/item-property-strings")
    public ResponseEntity<List<ItemPropertyStringDTO>> getAllItemPropertyStrings(ItemPropertyStringCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemPropertyStrings by criteria: {}", criteria);
        Page<ItemPropertyStringDTO> page = itemPropertyStringQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/item-property-strings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /item-property-strings/count : count all the itemPropertyStrings.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/item-property-strings/count")
    public ResponseEntity<Long> countItemPropertyStrings(ItemPropertyStringCriteria criteria) {
        log.debug("REST request to count ItemPropertyStrings by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemPropertyStringQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /item-property-strings/:id : get the "id" itemPropertyString.
     *
     * @param id the id of the itemPropertyStringDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemPropertyStringDTO, or with status 404 (Not Found)
     */
    @GetMapping("/item-property-strings/{id}")
    public ResponseEntity<ItemPropertyStringDTO> getItemPropertyString(@PathVariable Long id) {
        log.debug("REST request to get ItemPropertyString : {}", id);
        Optional<ItemPropertyStringDTO> itemPropertyStringDTO = itemPropertyStringService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemPropertyStringDTO);
    }

    /**
     * DELETE  /item-property-strings/:id : delete the "id" itemPropertyString.
     *
     * @param id the id of the itemPropertyStringDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-property-strings/{id}")
    public ResponseEntity<Void> deleteItemPropertyString(@PathVariable Long id) {
        log.debug("REST request to delete ItemPropertyString : {}", id);
        itemPropertyStringService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
