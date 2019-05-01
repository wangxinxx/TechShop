package com.isliam.techshop.web.rest;

import com.isliam.techshop.service.OrderService;
import com.isliam.techshop.service.dto.ItemCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * OrderControllerResource controller
 */
@RestController
@RequestMapping("/api/order-controller")
public class OrderResource {

    private final OrderService orderService;

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
    * POST makeAnOrder
    */
    @PostMapping("/make-an-order/{item_id}")
    public String makeAnOrder(final @PathVariable("item_id") Integer itemId) {
        orderService.makeAnOrder(itemId);
        return "makeAnOrder";
    }

    /**
    * POST approveOrder
    */
    @PostMapping("/approve-order/{order_id}")
    public String approveOrder(final @PathVariable("item_id") Integer itemId) {
        orderService.approveOrder(itemId);
        return "approveOrder";
    }

    /**
    * POST rejectAnOrder
    */
    @PostMapping("/reject-order/{order_id}")
    public String rejectAnOrder(final @PathVariable("item_id") Integer itemId) {
        orderService.rejectOrder(itemId);
        return "rejectAnOrder";
    }

    /**
    * GET items
    */
    @GetMapping("/items")
    public String items(ItemCriteria criteria, Pageable pageable) {
        orderService.getItems(criteria,pageable);
        return "items";
    }

}
