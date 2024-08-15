package dev.pichborith.ecommerce.product;

import dev.pichborith.ecommerce.category.Category;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
        Category category = Category.builder()
                                    .id(request.id())
                                    .build();
        return Product.builder()
                      .id(request.id())
                      .name(request.name())
                      .description(request.description())
                      .availableQuantity(request.availableQuantity())
                      .price(request.price())
                      .category(category)
                      .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(product.getId(),
                                   product.getName(),
                                   product.getName(),
                                   product.getAvailableQuantity(),
                                   product.getPrice(),
                                   product.getCategory().getId(),
                                   product.getCategory().getName(),
                                   product.getCategory().getDescription());
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product,
                                                             double quantity) {
        return new ProductPurchaseResponse(product.getId(),
                                           product.getName(),
                                           product.getDescription(),
                                           product.getPrice(),
                                           quantity);
    }
}
