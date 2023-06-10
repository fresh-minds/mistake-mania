package nl.freshminds.repository;

import nl.freshminds.model.Order;
import org.springframework.stereotype.Component;

// Dummy repository class. Nothing to see here...
@Component
public class OrderRepository {

    public long save(Order order) {
        throw new UnsupportedOperationException("This is just a dummy");
    }

    public Order find(long id) {
        throw new UnsupportedOperationException("This is just a dummy");
    }

    public void delete(long id) {
        throw new UnsupportedOperationException("This is just a dummy");
    }

}
