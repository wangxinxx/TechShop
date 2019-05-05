package com.isliam.techshop.repository;

import com.isliam.techshop.domain.Profile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Profile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {

    Optional<Profile> findOneByUserId(Long userId);

//    @Query("select e from Employee e")// where e.corporateEmail = ?#{principal.username}")
//    Profile current();
}
