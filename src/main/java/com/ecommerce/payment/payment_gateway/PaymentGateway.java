package com.ecommerce.payment.payment_gateway;

import java.math.BigDecimal;

public interface PaymentGateway {
    String generatePaymentLink(String orderId, String name, String phone, BigDecimal amount);
}
