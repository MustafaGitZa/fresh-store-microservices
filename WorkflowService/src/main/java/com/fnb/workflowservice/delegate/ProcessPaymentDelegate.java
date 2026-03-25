package com.fnb.workflowservice.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
public class ProcessPaymentDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long orderId = (Long) execution.getVariable("orderId");
        Double totalPrice = (Double) execution.getVariable("totalPrice");

        log.info("Processing payment for orderId: {} amount: {}", orderId, totalPrice);

        // Randomly approve or decline
        boolean approved = new Random().nextBoolean();
        String paymentStatus = approved ? "APPROVED" : "DECLINED";

        log.info("Payment {} for orderId: {}", paymentStatus, orderId);

        execution.setVariable("paymentStatus", paymentStatus);
    }
}
