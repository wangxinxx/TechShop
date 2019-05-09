package com.isliam.techshop.web.rest;
import com.isliam.techshop.service.RegionService;
import com.isliam.techshop.web.rest.errors.BadRequestAlertException;
import com.isliam.techshop.web.rest.util.HeaderUtil;
import com.isliam.techshop.web.rest.util.PaginationUtil;
import com.isliam.techshop.service.dto.RegionDTO;
import com.isliam.techshop.service.dto.RegionCriteria;
import com.isliam.techshop.service.RegionQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Region.
 */
@RestController
@RequestMapping("/api")
public class RegionResource {

    private final Logger log = LoggerFactory.getLogger(RegionResource.class);

    private static final String ENTITY_NAME = "region";

    private final RegionService regionService;

    private final RegionQueryService regionQueryService;

    public RegionResource(RegionService regionService, RegionQueryService regionQueryService) {
        this.regionService = regionService;
        this.regionQueryService = regionQueryService;
    }

    /**
     * POST  /regions : Create a new region.
     *
     * @param regionDTO the regionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new regionDTO, or with status 400 (Bad Request) if the region has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regions")
    public ResponseEntity<RegionDTO> createRegion(@RequestBody RegionDTO regionDTO) throws URISyntaxException {
        log.debug("REST request to save Region : {}", regionDTO);
        if (regionDTO.getId() != null) {
            throw new BadRequestAlertException("A new region cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegionDTO result = regionService.save(regionDTO);
        return ResponseEntity.created(new URI("/api/regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /regions : Updates an existing region.
     *
     * @param regionDTO the regionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated regionDTO,
     * or with status 400 (Bad Request) if the regionDTO is not valid,
     * or with status 500 (Internal Server Error) if the regionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regions")
    public ResponseEntity<RegionDTO> updateRegion(@RequestBody RegionDTO regionDTO) throws URISyntaxException {
        log.debug("REST request to update Region : {}", regionDTO);
        if (regionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegionDTO result = regionService.save(regionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, regionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /regions : get all the regions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of regions in body
     */
    @GetMapping("/regions")
    public ResponseEntity<List<RegionDTO>> getAllRegions(RegionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Regions by criteria: {}", criteria);
        Page<RegionDTO> page = regionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/regions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /regions/count : count all the regions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/regions/count")
    public ResponseEntity<Long> countRegions(RegionCriteria criteria) {
        log.debug("REST request to count Regions by criteria: {}", criteria);
        return ResponseEntity.ok().body(regionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /regions/:id : get the "id" region.
     *
     * @param id the id of the regionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the regionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/regions/{id}")
    public ResponseEntity<RegionDTO> getRegion(@PathVariable Long id) {
        log.debug("REST request to get Region : {}", id);
        Optional<RegionDTO> regionDTO = regionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regionDTO);
    }

    /**
     * DELETE  /regions/:id : delete the "id" region.
     *
     * @param id the id of the regionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/regions/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        log.debug("REST request to delete Region : {}", id);
        regionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
