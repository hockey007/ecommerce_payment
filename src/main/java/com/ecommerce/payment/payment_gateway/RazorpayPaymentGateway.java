package com.ecommerce.payment.payment_gateway;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class RazorpayPaymentGateway implements PaymentGateway {

    @Autowired
    RazorpayClient razorpayClient;

    @Override
    public String generatePaymentLink(String orderId, String name, String phone, BigDecimal amount) {
        try {
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount.multiply(BigDecimal.valueOf(100)));
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("accept_partial", false);
            // paymentLinkRequest.put("first_min_partial_amount", 100);

            Instant currentTime = Instant.now();
            Instant expiryTime = currentTime.plus(20, ChronoUnit.MINUTES);
            paymentLinkRequest.put("expire_by", expiryTime.getEpochSecond());

            paymentLinkRequest.put("reference_id", orderId);
            paymentLinkRequest.put("description", "Payment for order id" + orderId);

            JSONObject customer = new JSONObject();
            customer.put("name", name);
            customer.put("contact", phone);
            // customer.put("email", "test@example.com");

            paymentLinkRequest.put("customer", customer);
            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            // notify.put("email", true);
            paymentLinkRequest.put("reminder_enable", true);

            JSONObject notes = new JSONObject();
            notes.put("policy_name", "Product Order");
            paymentLinkRequest.put("notes", notes);

             paymentLinkRequest.put("callback_url", "http://127.0.0.1:5173/payment/status");
             paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
            return payment.get("short_url").toString();
        } catch (RazorpayException razorpayException) {
            throw new RuntimeException(razorpayException);
        }
    }
}
