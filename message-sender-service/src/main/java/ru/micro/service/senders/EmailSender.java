package ru.micro.service.senders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.micro.service.interfaces.Sender;

@Service
@PropertySource("classpath:mail.properties")
public class EmailSender implements Sender {
    @Autowired
    private JavaMailSender mailSender;

    @Value("spring.mail.username")
    private String fromUser;

    @Override
    public void send(String personContact, String article, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromUser);
        message.setTo(personContact);
        message.setText(text);
        message.setSubject(article);
        mailSender.send(message);
        System.out.println("Mail Send!");
    }
}
