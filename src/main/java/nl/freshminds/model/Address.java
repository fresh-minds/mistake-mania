package nl.freshminds.model;

public record Address(
        String street,
        String city,
        String zipCode,
        String country
) {
}
