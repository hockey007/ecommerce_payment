package com.ecommerce.payment.controller;

import com.ecommerce.payment.dto.PaymentRequestDto;
import com.ecommerce.payment.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class PaymentController {

    static final String PAYMENT_AUTHORIZED = "payment.authorized";
    static final String PAYMENT_CAPTURED = "payment.captured";
    static final String PAYMENT_FAILED = "payment.failed";
    static final String PAYMENT_LINK_PAID = "payment_link.paid";

    static final String PAYMENT_DOWNTIME_STARTED = "payment.downtime.started";
    static final String PAYMENT_DOWNTIME_RESOLVED = "payment.downtime.resolved";
    static final String PAYMENT_DOWNTIME_UPDATED = "payment.downtime.updated";

    @Autowired
    private IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public String initiatePayment(@RequestBody PaymentRequestDto paymentRequestDTO) {
        return paymentService.initiatePayment(
                paymentRequestDTO.getOrderId(),
                paymentRequestDTO.getName(),
                paymentRequestDTO.getPhone(),
                paymentRequestDTO.getAmount()
        );
    }

    @PostMapping("/webhook")
    public void paymentCallbackWebhook(@RequestBody String event) {
        System.out.println("Webhook called.");
        System.out.println(event);
    }

    private void handlePaymentSuccess(Map<String, Object> payload) {
        Map<String, Object> paymentDetails = (Map<String, Object>) payload.get("payload");
    }

    private void handlePaymentFailure(Map<String, Object> payload) {
        //
    }

    private void processPaymentEvent(Map<String, Object> payload) {
        String eventType = (String) payload.get("event");

        switch (eventType) {
            case PAYMENT_LINK_PAID:
                handlePaymentSuccess(payload);
            case PAYMENT_FAILED:
                handlePaymentFailure(payload);
        }
    }
}
