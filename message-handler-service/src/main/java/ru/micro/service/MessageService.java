package ru.micro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.micro.dto.Message;
import ru.micro.exceptions.NotValidException;
import ru.micro.exceptions.TooManyRequestsException;
import ru.micro.model.MailingStatus;
import ru.micro.repository.ContactsRepository;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

@Service
public class MessageService {
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    @Autowired
    private ContactsRepository repository;
    @Value("${spring.kafka.topics.mailing}")
    private String topicName;

    public void sendMessage(int userId, String userName, String article, String text) {
        if (userName != null && repository.findByUserNameAndUserId(userName, userId) == null) {
            throw new NotValidException("Не удалось найти пользователя с таким именем");
        }
        boolean anyMatch = repository.findAllByUserId(userId)
                .stream()
                .anyMatch(el -> el.getMailingStatus().equals(MailingStatus.IN_PROCESS));
        if (anyMatch)
            throw new TooManyRequestsException("Не все сообщения после прошлой рассылки были отправлены. Подождите пока все сообщения отправятся.");

        Message msg = new Message(userId, userName, article, text);
        kafkaTemplate.send(topicName, msg);
    }
}
