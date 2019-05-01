package com.isliam.techshop.service.impl;

import com.isliam.techshop.domain.Profile;
import com.isliam.techshop.repository.ProfileRepository;
import com.isliam.techshop.service.ItemQueryService;
import com.isliam.techshop.service.OrderService;
import com.isliam.techshop.service.ProfileService;
import com.isliam.techshop.service.dto.ItemCriteria;
import com.isliam.techshop.service.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final ItemQueryService itemQueryService;

    private final ProfileRepository profileRepository;

    private final ProfileService profileService;

    public OrderServiceImpl(ItemQueryService itemQueryService,ProfileRepository profileRepository, ProfileService profileService) {
        this.itemQueryService = itemQueryService;
        this.profileRepository = profileRepository;
        this.profileService = profileService;
    }

    @Override
    public void makeAnOrder(Integer item_id) {
        Map<String, Object> variables = new HashMap<String, Object>();

        Profile profile = profileService.getCurrentUserProfile();

        variables.put("seller_id", null);
        variables.put("customer_id", profile.getId());
        variables.put("curier_id", null);
        variables.put("order_approved", false);
        variables.put("item_transported", false);
        variables.put("paid", false);
//        ProcessInstance processInstance =
//            runtimeService.startProcessInstanceByKey("sell", variables);
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
