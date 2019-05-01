package com.isliam.techshop.service.impl;

import com.isliam.techshop.domain.User;
import com.isliam.techshop.service.ProfileService;
import com.isliam.techshop.domain.Profile;
import com.isliam.techshop.repository.ProfileRepository;
import com.isliam.techshop.service.UserService;
import com.isliam.techshop.service.dto.ProfileDTO;
import com.isliam.techshop.service.mapper.ProfileMapper;
import com.isliam.techshop.web.rest.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Profile.
 */
@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    private final UserService userService;

    public ProfileServiceImpl(ProfileRepository profileRepository, ProfileMapper profileMapper, UserService userService) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.userService = userService;
    }

    /**
     * Save a profile.
     *
     * @param profileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProfileDTO save(ProfileDTO profileDTO) {
        log.debug("Request to save Profile : {}", profileDTO);
        Profile profile = profileMapper.toEntity(profileDTO);
        profile = profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    /**
     * Get all the profiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profiles");
        return profileRepository.findAll(pageable)
            .map(profileMapper::toDto);
    }


    /**
     * Get one profile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProfileDTO> findOne(Long id) {
        log.debug("Request to get Profile : {}", id);
        return profileRepository.findById(id)
            .map(profileMapper::toDto);
    }

    /**
     * Delete the profile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Profile : {}", id);
        profileRepository.deleteById(id);
    }

    @Override
    public Profile getCurrentUserProfile() {
        User user = userService.getUserWithAuthorities()
            .orElseThrow(ResourceNotFoundException::new);

        return profileRepository.findOneByUserId(user.getId())
            .orElseThrow(ResourceNotFoundException::new);
    }
}
