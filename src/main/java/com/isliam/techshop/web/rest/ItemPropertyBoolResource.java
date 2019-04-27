package com.isliam.techshop.web.rest;
import com.isliam.techshop.service.ItemPropertyBoolService;
import com.isliam.techshop.web.rest.errors.BadRequestAlertException;
import com.isliam.techshop.web.rest.util.HeaderUtil;
import com.isliam.techshop.web.rest.util.PaginationUtil;
import com.isliam.techshop.service.dto.ItemPropertyBoolDTO;
import com.isliam.techshop.service.dto.ItemPropertyBoolCriteria;
import com.isliam.techshop.service.ItemPropertyBoolQueryService;
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
 * REST controller for managing ItemPropertyBool.
 */
@RestController
@RequestMapping("/api")
public class ItemPropertyBoolResource {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyBoolResource.class);

    private static final String ENTITY_NAME = "itemPropertyBool";

    private final ItemPropertyBoolService itemPropertyBoolService;

    private final ItemPropertyBoolQueryService itemPropertyBoolQueryService;

    public ItemPropertyBoolResource(ItemPropertyBoolService itemPropertyBoolService, ItemPropertyBoolQueryService itemPropertyBoolQueryService) {
        this.itemPropertyBoolService = itemPropertyBoolService;
        this.itemPropertyBoolQueryService = itemPropertyBoolQueryService;
    }

    /**
     * POST  /item-property-bools : Create a new itemPropertyBool.
     *
     * @param itemPropertyBoolDTO the itemPropertyBoolDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemPropertyBoolDTO, or with status 400 (Bad Request) if the itemPropertyBool has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-property-bools")
    public ResponseEntity<ItemPropertyBoolDTO> createItemPropertyBool(@Valid @RequestBody ItemPropertyBoolDTO itemPropertyBoolDTO) throws URISyntaxException {
        log.debug("REST request to save ItemPropertyBool : {}", itemPropertyBoolDTO);
        if (itemPropertyBoolDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemPropertyBool cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPropertyBoolDTO result = itemPropertyBoolService.save(itemPropertyBoolDTO);
        return ResponseEntity.created(new URI("/api/item-property-bools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-property-bools : Updates an existing itemPropertyBool.
     *
     * @param itemPropertyBoolDTO the itemPropertyBoolDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemPropertyBoolDTO,
     * or with status 400 (Bad Request) if the itemPropertyBoolDTO is not valid,
     * or with status 500 (Internal Server Error) if the itemPropertyBoolDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-property-bools")
    public ResponseEntity<ItemPropertyBoolDTO> updateItemPropertyBool(@Valid @RequestBody ItemPropertyBoolDTO itemPropertyBoolDTO) throws URISyntaxException {
        log.debug("REST request to update ItemPropertyBool : {}", itemPropertyBoolDTO);
        if (itemPropertyBoolDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemPropertyBoolDTO result = itemPropertyBoolService.save(itemPropertyBoolDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemPropertyBoolDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-property-bools : get all the itemPropertyBools.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of itemPropertyBools in body
     */
    @GetMapping("/item-property-bools")
    public ResponseEntity<List<ItemPropertyBoolDTO>> getAllItemPropertyBools(ItemPropertyBoolCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemPropertyBools by criteria: {}", criteria);
        Page<ItemPropertyBoolDTO> page = itemPropertyBoolQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/item-property-bools");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /item-property-bools/count : count all the itemPropertyBools.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/item-property-bools/count")
    public ResponseEntity<Long> countItemPropertyBools(ItemPropertyBoolCriteria criteria) {
        log.debug("REST request to count ItemPropertyBools by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemPropertyBoolQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /item-property-bools/:id : get the "id" itemPropertyBool.
     *
     * @param id the id of the itemPropertyBoolDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemPropertyBoolDTO, or with status 404 (Not Found)
     */
    @GetMapping("/item-property-bools/{id}")
    public ResponseEntity<ItemPropertyBoolDTO> getItemPropertyBool(@PathVariable Long id) {
        log.debug("REST request to get ItemPropertyBool : {}", id);
        Optional<ItemPropertyBoolDTO> itemPropertyBoolDTO = itemPropertyBoolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemPropertyBoolDTO);
    }

    /**
     * DELETE  /item-property-bools/:id : delete the "id" itemPropertyBool.
     *
     * @param id the id of the itemPropertyBoolDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-property-bools/{id}")
    public ResponseEntity<Void> deleteItemPropertyBool(@PathVariable Long id) {
        log.debug("REST request to delete ItemPropertyBool : {}", id);
        itemPropertyBoolService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
