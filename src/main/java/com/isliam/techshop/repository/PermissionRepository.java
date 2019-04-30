package com.isliam.techshop.repository;

import com.isliam.techshop.domain.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Permission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    @Query(value = "select distinct permission from Permission permission left join fetch permission.positions",
        countQuery = "select count(distinct permission) from Permission permission")
    Page<Permission> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct permission from Permission permission left join fetch permission.positions")
    List<Permission> findAllWithEagerRelationships();

    @Query("select permission from Permission permission left join fetch permission.positions where permission.id =:id")
    Optional<Permission> findOneWithEagerRelationships(@Param("id") Long id);

}
