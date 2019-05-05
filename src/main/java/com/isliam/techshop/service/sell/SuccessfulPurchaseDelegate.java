package com.isliam.techshop.service.sell;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SuccessfulPurchaseDelegate implements JavaDelegate {

    Logger log = LoggerFactory.getLogger(JavaDelegate.class);

	@Override
	public void execute(DelegateExecution execution) {
        log.info("-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n"
            +"-----------------------------------------SUCCESS-----------------------------------------------\n");
		// TODO Auto-generated method stub

	}

}
