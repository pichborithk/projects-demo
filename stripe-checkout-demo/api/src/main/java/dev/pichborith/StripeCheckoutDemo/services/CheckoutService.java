package dev.pichborith.StripeCheckoutDemo.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import dev.pichborith.StripeCheckoutDemo.models.CartItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    @Value("${STRIPE_API_KEY}")
    private String stripeApiKey;

    @Value("${CLIENT_URL}")
    private String clientUrl;

    public String checkout(CartItem item) throws StripeException {

        Stripe.apiKey = stripeApiKey;

        var productParams = ProductCreateParams.builder()
                                                               .setName(
                                                                   item.name())
                                                               .build();
        var product = Product.create(productParams);

        var priceParams = PriceCreateParams
                                            .builder()
                                            .setProduct(product.getId())
                                            .setUnitAmount(item.price())
                                            .setCurrency("usd")
                                            .build();
        var price = Price.create(priceParams);

        var sessionParams = SessionCreateParams
                                                .builder()
                                                .setMode(
                                                    SessionCreateParams.Mode.PAYMENT)
                                                .addLineItem(
                                                    SessionCreateParams.LineItem
                                                        .builder()
                                                        .setPrice(price.getId())
                                                        .setQuantity(
                                                            item.quantity())
                                                        .build()
                                                )
                                                .setSuccessUrl(clientUrl + "/checkout?success=true")
                                                .setCancelUrl(clientUrl + "/canceled?canceled=true")
                                                .build();
        return Session.create(sessionParams).getUrl();
    }
}
