package ru.micro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.micro.dto.Message;

@Service
public class MessageListener {
    @Autowired
    MessageSender sender;

    @KafkaListener(
            topics = "${spring.kafka.topics.mailing}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    void listener(Message message, Acknowledgment acknowledgment
    ) {
        System.out.println(message);
        sender.send(message);
        //коммит в kafka после завершения рассылки
        acknowledgment.acknowledge();
    }
}
