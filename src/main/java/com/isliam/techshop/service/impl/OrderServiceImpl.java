package com.isliam.techshop.service.impl;

import com.isliam.techshop.domain.Permission;
import com.isliam.techshop.domain.Profile;
import com.isliam.techshop.repository.ProfileRepository;
import com.isliam.techshop.service.ItemQueryService;
import com.isliam.techshop.service.OrderService;
import com.isliam.techshop.service.ProcessConstants;
import com.isliam.techshop.service.ProfileService;
import com.isliam.techshop.service.dto.ItemCriteria;
import com.isliam.techshop.service.dto.ItemDTO;
import com.isliam.techshop.service.dto.TaskDTO;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final ItemQueryService itemQueryService;

    private final ProfileRepository profileRepository;

    private final ProfileService profileService;

    private final RuntimeService runtimeService;

    private final TaskService taskService;

    private final HistoryService historyService;

    public OrderServiceImpl(ItemQueryService itemQueryService, ProfileRepository profileRepository, ProfileService profileService, RuntimeService runtimeService, TaskService taskService, HistoryService historyService) {
        this.itemQueryService = itemQueryService;
        this.profileRepository = profileRepository;
        this.profileService = profileService;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.historyService = historyService;
    }

    @Override
    public void makeAnOrder(Long item_id) {
        Map<String, Object> variables = new HashMap<String, Object>();

        Profile profile = profileService.getCurrentUserProfile();

        variables.put("seller_id", null);
        variables.put("customer_id", profile.getId()/*profile.getId()*/);
        variables.put("item_present", true/*profile.getId()*/);
        variables.put("curier_id", null);
        variables.put("order_approved", false);
        variables.put("item_transported", false);
        variables.put("paid", false);
        ProcessInstance processInstance =
            runtimeService.startProcessInstanceByKey(ProcessConstants.SELL_PROCESS, variables);
    }

    @Override
    public void rejectOrder(String taskId) {
        Profile profile = profileService.getCurrentUserProfile();
        Map<String, Object> variables = taskService.getVariables(taskId);
        variables.replace("seller_id", profile.getId());
        variables.replace("order_approved", false);
        taskService.complete(taskId.toString(), variables);
    }

    @Override
    public void approveOrder(String taskId) {
        Profile profile = profileService.getCurrentUserProfile();
        Map<String, Object> variables = taskService.getVariables(taskId);
        variables.replace("seller_id", profile.getId());
        variables.replace("order_approved", true);
        taskService.complete(taskId.toString(), variables);
    }

    @Override
    public Page<ItemDTO> getItems(ItemCriteria criteria, Pageable pageable) {
        return itemQueryService.findByCriteria(criteria,pageable);
    }

    @Override
    public Page<TaskDTO> getMyTasks(ItemCriteria criteria, Pageable pageable) {

        Profile profile = profileService.getCurrentUserProfile();

        List<TaskDTO> taskDTOS = new ArrayList<>();
        Objects.requireNonNull(profile.getPosition());
        Set<Permission> permissions = profile.getPosition().getPermissions();
        Objects.requireNonNull(permissions);

        for(Permission permission : profile.getPosition().getPermissions()){
            taskDTOS.addAll(
                taskService.createTaskQuery().taskCandidateGroup(permission.getName()).list()
                .stream().map(t->new TaskDTO(t.getId(),t.getName()))
                .collect(Collectors.toList()));
        }

        return new PageImpl<>(taskDTOS, pageable,taskDTOS.size());
    }

    @Override
    public Page<TaskDTO> getComplatedTasks(Pageable pageable) {

        List<TaskDTO> activities =
            historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(ProcessConstants.SELL_PROCESS)
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc()
                .list().stream().map(t->new TaskDTO(t.getActivityId(),t.getActivityName())).collect(Collectors.toList());



        return new PageImpl<>(activities, pageable,activities.size());
    }
}
