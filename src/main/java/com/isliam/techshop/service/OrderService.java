package com.isliam.techshop.service;

import com.isliam.techshop.service.dto.ItemCriteria;
import com.isliam.techshop.service.dto.ItemDTO;
import com.isliam.techshop.service.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    void makeAnOrder(Long itemId);

    void rejectOrder(String itemId);

    void approveOrder(String taskId);

    Page<ItemDTO> getItems(ItemCriteria criteria, Pageable pageable);

    Page<TaskDTO> getMyTasks(ItemCriteria criteria, Pageable pageable);

    Page<TaskDTO> getComplatedTasks(Pageable pageable);
}
