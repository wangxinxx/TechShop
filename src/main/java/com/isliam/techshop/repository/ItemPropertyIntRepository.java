package com.isliam.techshop.repository;

import com.isliam.techshop.domain.ItemPropertyInt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemPropertyInt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPropertyIntRepository extends JpaRepository<ItemPropertyInt, Long>, JpaSpecificationExecutor<ItemPropertyInt> {

}
