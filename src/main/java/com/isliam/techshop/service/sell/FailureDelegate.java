package com.isliam.techshop.service.sell;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FailureDelegate implements JavaDelegate {

    Logger log = LoggerFactory.getLogger(JavaDelegate.class);

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
    }

}
