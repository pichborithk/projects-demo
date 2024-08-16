package dev.pichborith.ecommerce.kafka;

import dev.pichborith.ecommerce.customer.CustomerResponse;
import dev.pichborith.ecommerce.order.PaymentMethod;
import dev.pichborith.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (
    String orderReference,
    BigDecimal totalAmount,
    PaymentMethod paymentMethod,
    CustomerResponse customer,
    List<PurchaseResponse> products

) {}