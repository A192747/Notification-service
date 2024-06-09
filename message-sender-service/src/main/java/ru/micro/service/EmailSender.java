package ru.micro.service;

import org.springframework.stereotype.Service;
import ru.micro.service.interfaces.Sender;

@Service
public class EmailSender implements Sender {
    @Override
    public void send(String personContact, String text) {

    }
}
