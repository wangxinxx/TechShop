package com.isliam.techshop.web.rest;
import com.isliam.techshop.service.ProfileService;
import com.isliam.techshop.web.rest.errors.BadRequestAlertException;
import com.isliam.techshop.web.rest.util.HeaderUtil;
import com.isliam.techshop.web.rest.util.PaginationUtil;
import com.isliam.techshop.service.dto.ProfileDTO;
import com.isliam.techshop.service.dto.ProfileCriteria;
import com.isliam.techshop.service.ProfileQueryService;
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
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing Profile.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(ProfileResource.class);

    private static final String ENTITY_NAME = "profile";

    private final ProfileService profileService;

    private final ProfileQueryService profileQueryService;

    public ProfileResource(ProfileService profileService, ProfileQueryService profileQueryService) {
        this.profileService = profileService;
        this.profileQueryService = profileQueryService;
    }

    /**
     * POST  /profiles : Create a new profile.
     *
     * @param profileDTO the profileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profileDTO, or with status 400 (Bad Request) if the profile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profiles")
    public ResponseEntity<ProfileDTO> createProfile(@Valid @RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        log.debug("REST request to save Profile : {}", profileDTO);
        if (profileDTO.getId() != null) {
            throw new BadRequestAlertException("A new profile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(profileDTO.getUserId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        ProfileDTO result = profileService.save(profileDTO);
        return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profiles : Updates an existing profile.
     *
     * @param profileDTO the profileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profileDTO,
     * or with status 400 (Bad Request) if the profileDTO is not valid,
     * or with status 500 (Internal Server Error) if the profileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profiles")
    public ResponseEntity<ProfileDTO> updateProfile(@Valid @RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        log.debug("REST request to update Profile : {}", profileDTO);
        if (profileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfileDTO result = profileService.save(profileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profiles : get all the profiles.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of profiles in body
     */
    @GetMapping("/profiles")
    public ResponseEntity<List<ProfileDTO>> getAllProfiles(ProfileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Profiles by criteria: {}", criteria);
        Page<ProfileDTO> page = profileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/profiles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /profiles/count : count all the profiles.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/profiles/count")
    public ResponseEntity<Long> countProfiles(ProfileCriteria criteria) {
        log.debug("REST request to count Profiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(profileQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /profiles/:id : get the "id" profile.
     *
     * @param id the id of the profileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/profiles/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) {
        log.debug("REST request to get Profile : {}", id);
        Optional<ProfileDTO> profileDTO = profileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profileDTO);
    }

    /**
     * DELETE  /profiles/:id : delete the "id" profile.
     *
     * @param id the id of the profileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profiles/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        log.debug("REST request to delete Profile : {}", id);
        profileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
