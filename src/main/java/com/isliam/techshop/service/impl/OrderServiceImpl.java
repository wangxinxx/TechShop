package com.isliam.techshop.service.impl;

import com.isliam.techshop.service.ItemQueryService;
import com.isliam.techshop.service.OrderService;
import com.isliam.techshop.service.dto.ItemCriteria;
import com.isliam.techshop.service.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final ItemQueryService itemQueryService;

    public OrderServiceImpl(ItemQueryService itemQueryService) {
        this.itemQueryService = itemQueryService;
    }

    @Override
    public void makeAnOrder(Integer item_id) {

    }

    @Override
    public void rejectOrder(Integer itemId) {

    }

    @Override
    public void approveOrder(Integer itemId) {

    }

    @Override
    public Page<ItemDTO> getItems(ItemCriteria criteria, Pageable pageable) {
        return itemQueryService.findByCriteria(criteria,pageable);
    }
}
