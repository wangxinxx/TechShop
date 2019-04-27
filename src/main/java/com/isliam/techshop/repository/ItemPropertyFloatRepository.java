package com.isliam.techshop.repository;

import com.isliam.techshop.domain.ItemPropertyFloat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemPropertyFloat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPropertyFloatRepository extends JpaRepository<ItemPropertyFloat, Long>, JpaSpecificationExecutor<ItemPropertyFloat> {

}
