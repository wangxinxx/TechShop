package com.isliam.techshop.web.rest;

import com.isliam.techshop.service.OrderService;
import com.isliam.techshop.service.dto.ItemCriteria;
import com.isliam.techshop.service.dto.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderControllerResource controller
 */
@RestController
@RequestMapping("/api/order")
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
    public void makeAnOrder(final @PathVariable("item_id") Long itemId) {
        orderService.makeAnOrder(itemId);
    }

    /**
    * POST approveOrder
    */
    @PostMapping("/approve-order/{order_id}")
    public void approveOrder(final @PathVariable("order_id") String itemId) {
        orderService.approveOrder(itemId);
    }

    /**
    * POST rejectAnOrder
    */
    @PostMapping("/reject-order/{order_id}")
    public void rejectAnOrder(final @PathVariable("order_id") String itemId) {
        orderService.rejectOrder(itemId);
    }

    /**
    * GET items
    */
    @GetMapping("/items")
    public String items(ItemCriteria criteria, Pageable pageable) {
        orderService.getItems(criteria,pageable);
        return "items";
    }

    /**
     * GET items
     */
    @GetMapping("/tasks")
    public List<TaskDTO> tasks(ItemCriteria criteria, Pageable pageable) {
        return orderService.getMyTasks(criteria,pageable).stream().collect(Collectors.toList());
    }


    /**
     * GET items
     */
    @GetMapping("/complatedTasks")
    public List<TaskDTO> complatedTasks(ItemCriteria criteria, Pageable pageable) {
        return orderService.getComplatedTasks(pageable).stream().collect(Collectors.toList());
    }


}
