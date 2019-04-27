package com.isliam.techshop.repository;

import com.isliam.techshop.domain.ItemPropertyBool;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemPropertyBool entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPropertyBoolRepository extends JpaRepository<ItemPropertyBool, Long>, JpaSpecificationExecutor<ItemPropertyBool> {

}
