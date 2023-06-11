package nl.freshminds.controller;

import nl.freshminds.exception.OrderNotFoundException;
import nl.freshminds.model.Order;
import nl.freshminds.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderRepository orderRepository;

    @GetMapping("/get/find-order-by-id")
    public Order findOrderById(@RequestParam(value = "id") long id) {
        try {
            return orderRepository.find(id);
        } catch (OrderNotFoundException exception) {
            logger.info("Failed to find order " + id);
            return null;
        }
    }

    @GetMapping("/delete/delete-order-by-id")
    public void deleteOrderById(@RequestParam(value = "id") long id) {
        try {
            orderRepository.delete(id);
        } catch (OrderNotFoundException exception) {
            throw exception;
        }
    }
}
