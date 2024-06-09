package ru.micro.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface Sender {
    void send(String personContact, String text);
}
