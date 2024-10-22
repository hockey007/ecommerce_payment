package com.ecommerce.payment.service;

import com.ecommerce.payment.payment_gateway.PaymentGateway;
import com.ecommerce.payment.payment_gateway.PaymentGatewaySelectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private PaymentGatewaySelectionStrategy paymentGatewaySelectionStrategy;

    @Override
    public String initiatePayment(String orderId, String name, String phone, BigDecimal amount) {
        PaymentGateway paymentGateway = paymentGatewaySelectionStrategy.getPaymentGateway();
        return paymentGateway.generatePaymentLink(orderId, name, phone, amount);
    }
}
