package demo.data.mqRedis.config;

public interface MessagePublisher {
    void publish(String message);
}