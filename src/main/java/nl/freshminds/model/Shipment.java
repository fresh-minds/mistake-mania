package nl.freshminds.model;

import java.util.List;

public record Shipment(
        Address address,
        List<Product> products,
        Box box
) {
}
