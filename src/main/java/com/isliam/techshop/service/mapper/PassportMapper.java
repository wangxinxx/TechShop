package com.isliam.techshop.service.mapper;

import com.isliam.techshop.domain.*;
import com.isliam.techshop.service.dto.PassportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Passport and its DTO PassportDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface PassportMapper extends EntityMapper<PassportDTO, Passport> {

    @Mapping(source = "profile.id", target = "profileId")
    PassportDTO toDto(Passport passport);

    @Mapping(source = "profileId", target = "profile")
    Passport toEntity(PassportDTO passportDTO);

    default Passport fromId(Long id) {
        if (id == null) {
            return null;
        }
        Passport passport = new Passport();
        passport.setId(id);
        return passport;
    }
}
