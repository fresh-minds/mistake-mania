package nl.freshminds.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.freshminds.controller.OrderController;
import nl.freshminds.model.Box;
import nl.freshminds.model.Order;
import nl.freshminds.model.Shipment;
import nl.freshminds.publisher.ShipmentPublisher;
import nl.freshminds.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderListener {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final ShipmentPublisher shipmentPublisher;
    private final ShipmentRepository shipmentRepository;

    @Value("box.service.url")
    private String url;

    public OrderListener(ShipmentPublisher shipmentPublisher, ShipmentRepository shipmentRepository) {
        this.shipmentPublisher = shipmentPublisher;
        this.shipmentRepository = shipmentRepository;
    }

    public void handleMessage(Message<Order> message) {
        logger.debug("Received message for order " + message.payload());

        // Acknowledge we successfully processed the message.
        message.ack();

        // Use the 'box service' to find the size of box we need to ship the order.
        var boxSize = Box.LARGE;
        try {
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper objectMapper = new ObjectMapper();
            var jsonRequestBody = objectMapper.writeValueAsString(message.payload().products());
            HttpEntity<String> request = new HttpEntity<>(jsonRequestBody, headers);
            RestTemplate restTemplate = new RestTemplate();
            boxSize = restTemplate.postForObject(url, request, Box.class);
        } catch (Exception e) {
            logger.warn("Failed to determine box size. Falling back to the biggest box", e);
        }

        var shipment = new Shipment(
                message.payload().address(),
                message.payload().products(),
                boxSize
        );

        shipmentPublisher.publish(shipment);
        shipmentRepository.save(shipment);
    }
}
