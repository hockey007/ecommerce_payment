package com.ecommerce.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequestDto {

    @JsonProperty("order_id")
    private String orderId;

    private String name;
    private String phone;
    private BigDecimal amount;

}
