package com.ecommerce.payment.payment_gateway;

import org.springframework.stereotype.Service;

@Service
public class PaymentGatewaySelectionStrategy {
    private RazorpayPaymentGateway razorpayPaymentGateway;

    public PaymentGatewaySelectionStrategy(RazorpayPaymentGateway razorpayPaymentGateway) {
        this.razorpayPaymentGateway = razorpayPaymentGateway;
    }

    public PaymentGateway getPaymentGateway() {
        return razorpayPaymentGateway;
    }
}
