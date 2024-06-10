package ru.micro.service;

import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.micro.dto.Message;
import ru.micro.exceptions.NotValidException;
import ru.micro.repository.ContactsRepository;

@Service
public class MessageService {
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    @Autowired
    private ContactsRepository repository;
    private final String topicName = "mailingTopic";

    public void sendMessage(int userId, String userName, String article, String text) {
        if (userName != null && repository.findByUserNameAndUserId(userName, userId) == null) {
            throw new NotValidException("Не удалось найти пользователя с таким именем");
        }
        Message msg = new Message(userId, userName, article, text);
        kafkaTemplate.send(topicName, msg);
    }
}
