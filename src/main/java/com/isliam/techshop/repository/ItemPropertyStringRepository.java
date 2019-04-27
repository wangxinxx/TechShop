package com.isliam.techshop.repository;

import com.isliam.techshop.domain.ItemPropertyString;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemPropertyString entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPropertyStringRepository extends JpaRepository<ItemPropertyString, Long>, JpaSpecificationExecutor<ItemPropertyString> {

}
