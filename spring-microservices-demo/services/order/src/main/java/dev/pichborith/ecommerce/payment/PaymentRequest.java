package dev.pichborith.ecommerce.payment;

import dev.pichborith.ecommerce.customer.CustomerResponse;
import dev.pichborith.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
    BigDecimal amount,
    PaymentMethod paymentMethod,
    Integer orderId,
    String orderReference,
    CustomerResponse customer
) {}