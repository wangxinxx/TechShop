package com.isliam.techshop.repository;

import com.isliam.techshop.domain.ItemPropertyDouble;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemPropertyDouble entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPropertyDoubleRepository extends JpaRepository<ItemPropertyDouble, Long>, JpaSpecificationExecutor<ItemPropertyDouble> {

}
