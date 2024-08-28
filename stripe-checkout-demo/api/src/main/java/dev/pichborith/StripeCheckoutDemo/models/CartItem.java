package dev.pichborith.StripeCheckoutDemo.models;


public record CartItem(
    int itemId,
    String name,
    Long price,
    Long quantity
) {}
