package com.isliam.techshop.service.mapper;

import com.isliam.techshop.domain.*;
import com.isliam.techshop.service.dto.PassportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Passport and its DTO PassportDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PassportMapper extends EntityMapper<PassportDTO, Passport> {



    default Passport fromId(Long id) {
        if (id == null) {
            return null;
        }
        Passport passport = new Passport();
        passport.setId(id);
        return passport;
    }
}
