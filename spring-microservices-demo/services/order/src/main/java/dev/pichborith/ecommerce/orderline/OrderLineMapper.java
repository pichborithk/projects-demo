package dev.pichborith.ecommerce.orderline;

import dev.pichborith.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        var order = Order.builder().id(request.orderId()).build();

        return OrderLine.builder()
                        .id(request.orderId())
                        .productId(request.productId())
                        .order(order)
                        .quantity(request.quantity())
                        .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
            orderLine.getId(),
            orderLine.getQuantity()
        );
    }
}