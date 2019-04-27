package com.isliam.techshop.web.rest;
import com.isliam.techshop.service.ItemPropertyDoubleService;
import com.isliam.techshop.web.rest.errors.BadRequestAlertException;
import com.isliam.techshop.web.rest.util.HeaderUtil;
import com.isliam.techshop.web.rest.util.PaginationUtil;
import com.isliam.techshop.service.dto.ItemPropertyDoubleDTO;
import com.isliam.techshop.service.dto.ItemPropertyDoubleCriteria;
import com.isliam.techshop.service.ItemPropertyDoubleQueryService;
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
 * REST controller for managing ItemPropertyDouble.
 */
@RestController
@RequestMapping("/api")
public class ItemPropertyDoubleResource {

    private final Logger log = LoggerFactory.getLogger(ItemPropertyDoubleResource.class);

    private static final String ENTITY_NAME = "itemPropertyDouble";

    private final ItemPropertyDoubleService itemPropertyDoubleService;

    private final ItemPropertyDoubleQueryService itemPropertyDoubleQueryService;

    public ItemPropertyDoubleResource(ItemPropertyDoubleService itemPropertyDoubleService, ItemPropertyDoubleQueryService itemPropertyDoubleQueryService) {
        this.itemPropertyDoubleService = itemPropertyDoubleService;
        this.itemPropertyDoubleQueryService = itemPropertyDoubleQueryService;
    }

    /**
     * POST  /item-property-doubles : Create a new itemPropertyDouble.
     *
     * @param itemPropertyDoubleDTO the itemPropertyDoubleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemPropertyDoubleDTO, or with status 400 (Bad Request) if the itemPropertyDouble has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-property-doubles")
    public ResponseEntity<ItemPropertyDoubleDTO> createItemPropertyDouble(@Valid @RequestBody ItemPropertyDoubleDTO itemPropertyDoubleDTO) throws URISyntaxException {
        log.debug("REST request to save ItemPropertyDouble : {}", itemPropertyDoubleDTO);
        if (itemPropertyDoubleDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemPropertyDouble cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPropertyDoubleDTO result = itemPropertyDoubleService.save(itemPropertyDoubleDTO);
        return ResponseEntity.created(new URI("/api/item-property-doubles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-property-doubles : Updates an existing itemPropertyDouble.
     *
     * @param itemPropertyDoubleDTO the itemPropertyDoubleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemPropertyDoubleDTO,
     * or with status 400 (Bad Request) if the itemPropertyDoubleDTO is not valid,
     * or with status 500 (Internal Server Error) if the itemPropertyDoubleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-property-doubles")
    public ResponseEntity<ItemPropertyDoubleDTO> updateItemPropertyDouble(@Valid @RequestBody ItemPropertyDoubleDTO itemPropertyDoubleDTO) throws URISyntaxException {
        log.debug("REST request to update ItemPropertyDouble : {}", itemPropertyDoubleDTO);
        if (itemPropertyDoubleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemPropertyDoubleDTO result = itemPropertyDoubleService.save(itemPropertyDoubleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemPropertyDoubleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-property-doubles : get all the itemPropertyDoubles.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of itemPropertyDoubles in body
     */
    @GetMapping("/item-property-doubles")
    public ResponseEntity<List<ItemPropertyDoubleDTO>> getAllItemPropertyDoubles(ItemPropertyDoubleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemPropertyDoubles by criteria: {}", criteria);
        Page<ItemPropertyDoubleDTO> page = itemPropertyDoubleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/item-property-doubles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /item-property-doubles/count : count all the itemPropertyDoubles.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/item-property-doubles/count")
    public ResponseEntity<Long> countItemPropertyDoubles(ItemPropertyDoubleCriteria criteria) {
        log.debug("REST request to count ItemPropertyDoubles by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemPropertyDoubleQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /item-property-doubles/:id : get the "id" itemPropertyDouble.
     *
     * @param id the id of the itemPropertyDoubleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemPropertyDoubleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/item-property-doubles/{id}")
    public ResponseEntity<ItemPropertyDoubleDTO> getItemPropertyDouble(@PathVariable Long id) {
        log.debug("REST request to get ItemPropertyDouble : {}", id);
        Optional<ItemPropertyDoubleDTO> itemPropertyDoubleDTO = itemPropertyDoubleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemPropertyDoubleDTO);
    }

    /**
     * DELETE  /item-property-doubles/:id : delete the "id" itemPropertyDouble.
     *
     * @param id the id of the itemPropertyDoubleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-property-doubles/{id}")
    public ResponseEntity<Void> deleteItemPropertyDouble(@PathVariable Long id) {
        log.debug("REST request to delete ItemPropertyDouble : {}", id);
        itemPropertyDoubleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
