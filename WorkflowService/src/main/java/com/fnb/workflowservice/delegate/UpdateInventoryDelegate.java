package com.fnb.workflowservice.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateInventoryDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long orderId = (Long) execution.getVariable("orderId");
        String productName = (String) execution.getVariable("productName");
        Integer quantity = (Integer) execution.getVariable("quantity");

        log.info("Updating inventory for orderId: {} product: {} qty: {}",
                orderId, productName, quantity);

        execution.setVariable("inventoryStatus", "DEDUCTED");
    }
}
