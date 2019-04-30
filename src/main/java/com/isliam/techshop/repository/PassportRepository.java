package com.isliam.techshop.repository;

import com.isliam.techshop.domain.Passport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Passport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PassportRepository extends JpaRepository<Passport, Long>, JpaSpecificationExecutor<Passport> {

}
