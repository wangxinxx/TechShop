package com.isliam.techshop.web.rest;
import com.isliam.techshop.service.ItemPropertyIntService;
import com.isliam.techshop.web.rest.errors.BadRequestAlertException;
import com.isliam.techshop.web.rest.util.HeaderUtil;
import com.isliam.techshop.web.rest.util.PaginationUtil;
import com.isliam.techshop.service.dto.ItemPropertyIntDTO;
import com.isliam.techshop.service.dto.ItemPropertyIntCriteria;
import com.isliam.techshop.service.ItemPropertyIntQueryService;
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
 * REST controller for managing ItemPropertyInt.
 */
@RestController
@RequestMapping("/api")
public class ItemPropertyIntResource {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyIntResource.class);

    private static final String ENTITY_NAME = "itemPropertyInt";

    private final ItemPropertyIntService itemPropertyIntService;

    private final ItemPropertyIntQueryService itemPropertyIntQueryService;

    public ItemPropertyIntResource(ItemPropertyIntService itemPropertyIntService, ItemPropertyIntQueryService itemPropertyIntQueryService) {
        this.itemPropertyIntService = itemPropertyIntService;
        this.itemPropertyIntQueryService = itemPropertyIntQueryService;
    }

    /**
     * POST  /item-property-ints : Create a new itemPropertyInt.
     *
     * @param itemPropertyIntDTO the itemPropertyIntDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemPropertyIntDTO, or with status 400 (Bad Request) if the itemPropertyInt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-property-ints")
    public ResponseEntity<ItemPropertyIntDTO> createItemPropertyInt(@Valid @RequestBody ItemPropertyIntDTO itemPropertyIntDTO) throws URISyntaxException {
        log.debug("REST request to save ItemPropertyInt : {}", itemPropertyIntDTO);
        if (itemPropertyIntDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemPropertyInt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPropertyIntDTO result = itemPropertyIntService.save(itemPropertyIntDTO);
        return ResponseEntity.created(new URI("/api/item-property-ints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-property-ints : Updates an existing itemPropertyInt.
     *
     * @param itemPropertyIntDTO the itemPropertyIntDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemPropertyIntDTO,
     * or with status 400 (Bad Request) if the itemPropertyIntDTO is not valid,
     * or with status 500 (Internal Server Error) if the itemPropertyIntDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-property-ints")
    public ResponseEntity<ItemPropertyIntDTO> updateItemPropertyInt(@Valid @RequestBody ItemPropertyIntDTO itemPropertyIntDTO) throws URISyntaxException {
        log.debug("REST request to update ItemPropertyInt : {}", itemPropertyIntDTO);
        if (itemPropertyIntDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemPropertyIntDTO result = itemPropertyIntService.save(itemPropertyIntDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemPropertyIntDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-property-ints : get all the itemPropertyInts.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of itemPropertyInts in body
     */
    @GetMapping("/item-property-ints")
    public ResponseEntity<List<ItemPropertyIntDTO>> getAllItemPropertyInts(ItemPropertyIntCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemPropertyInts by criteria: {}", criteria);
        Page<ItemPropertyIntDTO> page = itemPropertyIntQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/item-property-ints");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /item-property-ints/count : count all the itemPropertyInts.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/item-property-ints/count")
    public ResponseEntity<Long> countItemPropertyInts(ItemPropertyIntCriteria criteria) {
        log.debug("REST request to count ItemPropertyInts by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemPropertyIntQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /item-property-ints/:id : get the "id" itemPropertyInt.
     *
     * @param id the id of the itemPropertyIntDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemPropertyIntDTO, or with status 404 (Not Found)
     */
    @GetMapping("/item-property-ints/{id}")
    public ResponseEntity<ItemPropertyIntDTO> getItemPropertyInt(@PathVariable Long id) {
        log.debug("REST request to get ItemPropertyInt : {}", id);
        Optional<ItemPropertyIntDTO> itemPropertyIntDTO = itemPropertyIntService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemPropertyIntDTO);
    }

    /**
     * DELETE  /item-property-ints/:id : delete the "id" itemPropertyInt.
     *
     * @param id the id of the itemPropertyIntDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-property-ints/{id}")
    public ResponseEntity<Void> deleteItemPropertyInt(@PathVariable Long id) {
        log.debug("REST request to delete ItemPropertyInt : {}", id);
        itemPropertyIntService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
