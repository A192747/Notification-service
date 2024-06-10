package ru.micro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.micro.dto.Message;

@Service
public class MessageListener {
    @Autowired
    MessageSender sender;

    @KafkaListener(topics = "mailingTopic", groupId = "mailing")
    void listener(Message message) {
        System.out.println(message);
        sender.send(message);
    }
}
