package com.fnb.workflowservice.config;

import com.fnb.workflowservice.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkflowListener {

    private final RuntimeService runtimeService;

    @KafkaListener(topics = "order-created", groupId = "workflow-group")
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("Starting workflow for orderId: {}", event.getOrderId());

        Map<String, Object> variables = new HashMap<>();
        variables.put("orderId", event.getOrderId());
        variables.put("customerId", event.getCustomerId());
        variables.put("productName", event.getProductName());
        variables.put("quantity", event.getQuantity());
        variables.put("totalPrice", event.getTotalPrice());

        runtimeService.startProcessInstanceByKey(
                "order-fulfillment",
                String.valueOf(event.getOrderId()),
                variables);

        log.info("Workflow started for orderId: {}", event.getOrderId());
    }
}
