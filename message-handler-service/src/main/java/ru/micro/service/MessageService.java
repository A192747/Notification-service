package ru.micro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.micro.dto.Message;

@Service
public class MessageService {
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    private final String topicName = "mailingTopic";

    public void sendMessage(int userId, String text) {
        Message msg = new Message(userId, text);
        kafkaTemplate.send(topicName, msg);
    }
}
