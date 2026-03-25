package com.fnb.workflowservice.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConfirmDeliveryDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long orderId = (Long) execution.getVariable("orderId");
        String productName = (String) execution.getVariable("productName");

        log.info("Confirming delivery for orderId: {} product: {}",
                orderId, productName);

        execution.setVariable("deliveryStatus", "CONFIRMED");
    }
}
