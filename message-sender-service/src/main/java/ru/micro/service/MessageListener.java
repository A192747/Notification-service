package ru.micro.service;

import com.sun.jdi.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.micro.dto.Message;

import java.util.Random;

@Service
public class MessageListener {
    @Autowired
    MessageSender sender;
    @Value("${mailing.retrying-count}")
    private int maxRetries; // Максимальное количество попыток
    private int retryCount = 0; // Счетчик текущей попытки

    @KafkaListener(
            topics = "${spring.kafka.topics.mailing}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    void listener(Message message, Acknowledgment acknowledgment
    ) {
        System.out.println(message);

        boolean sentSuccessfully = false;
        while (!sentSuccessfully && retryCount < maxRetries) {
            try {
                sender.send(message);
                System.out.println("Messages sent successfully.");
                sentSuccessfully = true;
            } catch (Exception e) {
                System.err.println("Failed to send messages due to error: " + e.getMessage());
                retryCount++;
                if (retryCount >= maxRetries) {
                    System.err.println("Max retries reached for message.");
                }
            }
        }

        if (sentSuccessfully) {
            acknowledgment.acknowledge(); //коммит в kafka после завершения рассылки
        }
    }
}
