package com.isliam.techshop.web.rest;
import com.isliam.techshop.service.CityService;
import com.isliam.techshop.web.rest.errors.BadRequestAlertException;
import com.isliam.techshop.web.rest.util.HeaderUtil;
import com.isliam.techshop.web.rest.util.PaginationUtil;
import com.isliam.techshop.service.dto.CityDTO;
import com.isliam.techshop.service.dto.CityCriteria;
import com.isliam.techshop.service.CityQueryService;
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
 * REST controller for managing City.
 */
@RestController
@RequestMapping("/api")
public class CityResource {

    private final Logger log = LoggerFactory.getLogger(CityResource.class);

    private static final String ENTITY_NAME = "city";

    private final CityService cityService;

    private final CityQueryService cityQueryService;

    public CityResource(CityService cityService, CityQueryService cityQueryService) {
        this.cityService = cityService;
        this.cityQueryService = cityQueryService;
    }

    /**
     * POST  /cities : Create a new city.
     *
     * @param cityDTO the cityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cityDTO, or with status 400 (Bad Request) if the city has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cities")
    public ResponseEntity<CityDTO> createCity(@Valid @RequestBody CityDTO cityDTO) throws URISyntaxException {
        log.debug("REST request to save City : {}", cityDTO);
        if (cityDTO.getId() != null) {
            throw new BadRequestAlertException("A new city cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CityDTO result = cityService.save(cityDTO);
        return ResponseEntity.created(new URI("/api/cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cities : Updates an existing city.
     *
     * @param cityDTO the cityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cityDTO,
     * or with status 400 (Bad Request) if the cityDTO is not valid,
     * or with status 500 (Internal Server Error) if the cityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cities")
    public ResponseEntity<CityDTO> updateCity(@Valid @RequestBody CityDTO cityDTO) throws URISyntaxException {
        log.debug("REST request to update City : {}", cityDTO);
        if (cityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CityDTO result = cityService.save(cityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cities : get all the cities.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of cities in body
     */
    @GetMapping("/cities")
    public ResponseEntity<List<CityDTO>> getAllCities(CityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Cities by criteria: {}", criteria);
        Page<CityDTO> page = cityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cities");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /cities/count : count all the cities.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/cities/count")
    public ResponseEntity<Long> countCities(CityCriteria criteria) {
        log.debug("REST request to count Cities by criteria: {}", criteria);
        return ResponseEntity.ok().body(cityQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /cities/:id : get the "id" city.
     *
     * @param id the id of the cityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cities/{id}")
    public ResponseEntity<CityDTO> getCity(@PathVariable Long id) {
        log.debug("REST request to get City : {}", id);
        Optional<CityDTO> cityDTO = cityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cityDTO);
    }

    /**
     * DELETE  /cities/:id : delete the "id" city.
     *
     * @param id the id of the cityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cities/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        log.debug("REST request to delete City : {}", id);
        cityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
