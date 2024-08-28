package dev.pichborith.StripeCheckoutDemo.models;

import java.util.List;

public record CartRequest(
    int id,
    List<CartItem> items
) {
}
