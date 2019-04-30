package com.isliam.techshop.web.rest;
import com.isliam.techshop.service.PositionService;
import com.isliam.techshop.web.rest.errors.BadRequestAlertException;
import com.isliam.techshop.web.rest.util.HeaderUtil;
import com.isliam.techshop.web.rest.util.PaginationUtil;
import com.isliam.techshop.service.dto.PositionDTO;
import com.isliam.techshop.service.dto.PositionCriteria;
import com.isliam.techshop.service.PositionQueryService;
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
 * REST controller for managing Position.
 */
@RestController
@RequestMapping("/api")
public class PositionResource {

    private final Logger log = LoggerFactory.getLogger(PositionResource.class);

    private static final String ENTITY_NAME = "position";

    private final PositionService positionService;

    private final PositionQueryService positionQueryService;

    public PositionResource(PositionService positionService, PositionQueryService positionQueryService) {
        this.positionService = positionService;
        this.positionQueryService = positionQueryService;
    }

    /**
     * POST  /positions : Create a new position.
     *
     * @param positionDTO the positionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new positionDTO, or with status 400 (Bad Request) if the position has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/positions")
    public ResponseEntity<PositionDTO> createPosition(@Valid @RequestBody PositionDTO positionDTO) throws URISyntaxException {
        log.debug("REST request to save Position : {}", positionDTO);
        if (positionDTO.getId() != null) {
            throw new BadRequestAlertException("A new position cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PositionDTO result = positionService.save(positionDTO);
        return ResponseEntity.created(new URI("/api/positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /positions : Updates an existing position.
     *
     * @param positionDTO the positionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated positionDTO,
     * or with status 400 (Bad Request) if the positionDTO is not valid,
     * or with status 500 (Internal Server Error) if the positionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/positions")
    public ResponseEntity<PositionDTO> updatePosition(@Valid @RequestBody PositionDTO positionDTO) throws URISyntaxException {
        log.debug("REST request to update Position : {}", positionDTO);
        if (positionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PositionDTO result = positionService.save(positionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, positionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /positions : get all the positions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of positions in body
     */
    @GetMapping("/positions")
    public ResponseEntity<List<PositionDTO>> getAllPositions(PositionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Positions by criteria: {}", criteria);
        Page<PositionDTO> page = positionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/positions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /positions/count : count all the positions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/positions/count")
    public ResponseEntity<Long> countPositions(PositionCriteria criteria) {
        log.debug("REST request to count Positions by criteria: {}", criteria);
        return ResponseEntity.ok().body(positionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /positions/:id : get the "id" position.
     *
     * @param id the id of the positionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the positionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/positions/{id}")
    public ResponseEntity<PositionDTO> getPosition(@PathVariable Long id) {
        log.debug("REST request to get Position : {}", id);
        Optional<PositionDTO> positionDTO = positionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(positionDTO);
    }

    /**
     * DELETE  /positions/:id : delete the "id" position.
     *
     * @param id the id of the positionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/positions/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        log.debug("REST request to delete Position : {}", id);
        positionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
