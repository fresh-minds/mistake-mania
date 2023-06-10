package nl.freshminds.model;

import java.util.List;

public record Order(
        Customer customer,
        Address address,
        List<Product> products
) {
}
