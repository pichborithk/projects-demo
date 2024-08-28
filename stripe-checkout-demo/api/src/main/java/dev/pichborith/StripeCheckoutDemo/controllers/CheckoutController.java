package dev.pichborith.StripeCheckoutDemo.controllers;

import com.stripe.exception.StripeException;
import dev.pichborith.StripeCheckoutDemo.models.CartItem;
import dev.pichborith.StripeCheckoutDemo.services.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/checkout")
    public ResponseEntity<Map<String, String>> checkout(
        @RequestBody CartItem item) throws StripeException {
        var sessionUrl = checkoutService.checkout(item);
        return ResponseEntity.ok( new HashMap<>() {{
            put("url", sessionUrl);
        }});

    }

}
