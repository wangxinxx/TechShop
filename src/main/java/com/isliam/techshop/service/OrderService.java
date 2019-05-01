package com.isliam.techshop.service;

import com.isliam.techshop.service.dto.ItemCriteria;
import com.isliam.techshop.service.dto.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    void makeAnOrder(Integer itemId);

    void rejectOrder(Integer itemId);

    void approveOrder(Integer itemId);

    Page<ItemDTO> getItems(ItemCriteria criteria, Pageable pageable);
}
