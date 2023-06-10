package nl.freshminds.model;

import java.math.BigDecimal;

public record Product(
        String name,
        String sku,
        BigDecimal price,
        double weight
) {
}
