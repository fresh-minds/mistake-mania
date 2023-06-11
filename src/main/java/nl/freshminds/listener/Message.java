package nl.freshminds.listener;

import java.util.Objects;

public record Message<T>(T payload) {

    public void ack() {
        // dummy operation
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message<?> message = (Message<?>) o;

        return Objects.equals(payload, message.payload);
    }

}
