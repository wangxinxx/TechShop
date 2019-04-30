package com.isliam.techshop.web.rest;
import com.isliam.techshop.service.PassportService;
import com.isliam.techshop.web.rest.errors.BadRequestAlertException;
import com.isliam.techshop.web.rest.util.HeaderUtil;
import com.isliam.techshop.web.rest.util.PaginationUtil;
import com.isliam.techshop.service.dto.PassportDTO;
import com.isliam.techshop.service.dto.PassportCriteria;
import com.isliam.techshop.service.PassportQueryService;
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
 * REST controller for managing Passport.
 */
@RestController
@RequestMapping("/api")
public class PassportResource {

    private final Logger log = LoggerFactory.getLogger(PassportResource.class);

    private static final String ENTITY_NAME = "passport";

    private final PassportService passportService;

    private final PassportQueryService passportQueryService;

    public PassportResource(PassportService passportService, PassportQueryService passportQueryService) {
        this.passportService = passportService;
        this.passportQueryService = passportQueryService;
    }

    /**
     * POST  /passports : Create a new passport.
     *
     * @param passportDTO the passportDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new passportDTO, or with status 400 (Bad Request) if the passport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/passports")
    public ResponseEntity<PassportDTO> createPassport(@Valid @RequestBody PassportDTO passportDTO) throws URISyntaxException {
        log.debug("REST request to save Passport : {}", passportDTO);
        if (passportDTO.getId() != null) {
            throw new BadRequestAlertException("A new passport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PassportDTO result = passportService.save(passportDTO);
        return ResponseEntity.created(new URI("/api/passports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /passports : Updates an existing passport.
     *
     * @param passportDTO the passportDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated passportDTO,
     * or with status 400 (Bad Request) if the passportDTO is not valid,
     * or with status 500 (Internal Server Error) if the passportDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/passports")
    public ResponseEntity<PassportDTO> updatePassport(@Valid @RequestBody PassportDTO passportDTO) throws URISyntaxException {
        log.debug("REST request to update Passport : {}", passportDTO);
        if (passportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PassportDTO result = passportService.save(passportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, passportDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /passports : get all the passports.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of passports in body
     */
    @GetMapping("/passports")
    public ResponseEntity<List<PassportDTO>> getAllPassports(PassportCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Passports by criteria: {}", criteria);
        Page<PassportDTO> page = passportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/passports");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /passports/count : count all the passports.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/passports/count")
    public ResponseEntity<Long> countPassports(PassportCriteria criteria) {
        log.debug("REST request to count Passports by criteria: {}", criteria);
        return ResponseEntity.ok().body(passportQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /passports/:id : get the "id" passport.
     *
     * @param id the id of the passportDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the passportDTO, or with status 404 (Not Found)
     */
    @GetMapping("/passports/{id}")
    public ResponseEntity<PassportDTO> getPassport(@PathVariable Long id) {
        log.debug("REST request to get Passport : {}", id);
        Optional<PassportDTO> passportDTO = passportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(passportDTO);
    }

    /**
     * DELETE  /passports/:id : delete the "id" passport.
     *
     * @param id the id of the passportDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/passports/{id}")
    public ResponseEntity<Void> deletePassport(@PathVariable Long id) {
        log.debug("REST request to delete Passport : {}", id);
        passportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
