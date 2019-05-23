package com.isliam.techshop.service.sell;

import com.isliam.techshop.config.DIUtils;
import com.isliam.techshop.domain.Notification;
import com.isliam.techshop.domain.Profile;
import com.isliam.techshop.repository.NotificationRepository;
import com.isliam.techshop.repository.ProfileRepository;
import com.isliam.techshop.service.util.RandomUtil;
import com.isliam.techshop.web.rest.errors.ResourceNotFoundException;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import static com.isliam.techshop.service.sell.BPMNVariables.*;
public class ItemPresentDelegate implements JavaDelegate {

    private Logger log = LoggerFactory.getLogger(ItemPresentDelegate.class);


    @Override
	public void execute(DelegateExecution execution) {
        Random random = new Random();
        boolean itemPresentRes = random.nextBoolean();
        execution.setVariable(itemPresent,itemPresentRes);

        ProfileRepository profileRepository = (ProfileRepository) DIUtils.getBean(ProfileRepository.class);
        NotificationRepository notificationRepository = (NotificationRepository)DIUtils.getBean(NotificationRepository.class);
        log.info("-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n");
        Notification notification = new Notification();
        Long customerId = Long.valueOf(execution.getVariable(BPMNVariables.customerId).toString());
        Long sellerId = Long.valueOf(execution.getVariable(BPMNVariables.sellerId).toString());

        Profile seller = profileRepository.findById(sellerId)
            .orElseThrow(ResourceNotFoundException::new);


        String message;

        if(itemPresentRes){
            message = "Item present and, order succeeded";
        }
        else {
            message = "Sorry, item is not present. Try later";
        }
        String.format("Order approved by %s %s",seller.getUser().getFirstName(), seller.getUser().getLastName());

        Profile profile = new Profile();
        profile.setId(customerId);

        notification.setProfile(profile);
        notification.setMessage(message);
        notification.setViewed(false);

        notificationRepository.save(notification);
		// TODO Auto-generated method stub

	}

}
