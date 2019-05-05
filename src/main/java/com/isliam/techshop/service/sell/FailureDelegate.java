package com.isliam.techshop.service.sell;

import com.isliam.techshop.domain.Notification;
import com.isliam.techshop.domain.Profile;
import com.isliam.techshop.repository.NotificationRepository;
import com.isliam.techshop.repository.ProfileRepository;
import com.isliam.techshop.web.rest.errors.ResourceNotFoundException;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FailureDelegate implements JavaDelegate {

    Logger log = LoggerFactory.getLogger(FailureDelegate.class);


    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void execute(DelegateExecution execution) {
        log.info("-----------------------------------------FAILURE-----------------------------------------------\n"
            + "-----------------------------------------FAILURE-----------------------------------------------\n"
            + "-----------------------------------------FAILURE-----------------------------------------------\n"
            + "-----------------------------------------FAILURE-----------------------------------------------\n"
            + "-----------------------------------------FAILURE-----------------------------------------------\n"
            + "-----------------------------------------FAILURE-----------------------------------------------\n"
            + "-----------------------------------------FAILURE-----------------------------------------------\n"
            + "-----------------------------------------FAILURE-----------------------------------------------\n"
            + "-----------------------------------------FAILURE-----------------------------------------------\n");


        Notification notification = new Notification();
        Long customerId = Long.valueOf(execution.getVariable("customer_id").toString());
        Long sellerId = Long.valueOf(execution.getVariable("customer_id").toString());
        Profile seller = profileRepository.findById(sellerId).orElseThrow(ResourceNotFoundException::new);
        String message =  String.format("Order rejected by {} {}",seller.getUser().getFirstName(), seller.getUser().getLastName());

        Profile profile = new Profile();
        profile.setId(customerId);

        notification.setProfile(profile);
        notification.setMessage(message);
        notification.setViewed(false);

        notificationRepository.save(notification);
    }

}
