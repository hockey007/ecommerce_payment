package com.ecommerce.payment.service;

import java.math.BigDecimal;

public interface IPaymentService {
    String initiatePayment(String orderId, String name, String phone, BigDecimal amount);
}
