package dev.pichborith.ecommerce.order;

import dev.pichborith.ecommerce.customer.CustomerClient;
import dev.pichborith.ecommerce.customer.CustomerResponse;
import dev.pichborith.ecommerce.exception.BusinessException;
import dev.pichborith.ecommerce.kafka.OrderConfirmation;
import dev.pichborith.ecommerce.kafka.OrderProducer;
import dev.pichborith.ecommerce.orderline.OrderLineRequest;
import dev.pichborith.ecommerce.orderline.OrderLineService;
import dev.pichborith.ecommerce.product.ProductClient;
import dev.pichborith.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
//    private final PaymentClient paymentClient;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        // Check the customer --> OpenFeign
        var customer = customerClient.findCustomerById(
                                         request.customerId())
                                     .orElseThrow(
                                         () -> new BusinessException(
                                             "Cannot create order:: No customer exists with the provided ID:: " + request.customerId()));

        // purchase the products --> product-service (RestTemplate)
        var purchasedProducts = productClient.purchaseProducts(
            request.products());

        // persist order
        var order = repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                new OrderLineRequest(
                    null,
                    order.getId(),
                    purchaseRequest.productId(),
                    purchaseRequest.quantity()
                )
            );
        }

        // start payment process
//        var paymentRequest = new PaymentRequest(
//            request.amount(),
//            request.paymentMethod(),
//            order.getId(),
//            order.getReference(),
//            customer
//        );
//        paymentClient.requestOrderPayment(paymentRequest);

        // send the order confirmation --> notification-service (kafka)
        orderProducer.sendOrderConfirmation(
            new OrderConfirmation(
                request.reference(),
                request.amount(),
                request.paymentMethod(),
                customer,
                purchasedProducts
            )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                              .stream()
                              .map(mapper::fromOrder)
                              .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                              .map(this.mapper::fromOrder)
                              .orElseThrow(() -> new EntityNotFoundException(
                                  String.format(
                                      "No order found with the provided ID: %d",
                                      id)));
    }
}
