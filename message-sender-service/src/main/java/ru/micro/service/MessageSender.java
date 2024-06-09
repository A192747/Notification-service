package ru.micro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.micro.dto.Message;
import ru.micro.entity.Contacts;
import ru.micro.model.MailingStatus;
import ru.micro.repository.ContactsRepository;
import ru.micro.service.interfaces.Sender;

import java.util.List;

@Service
public class MessageSender {
    @Autowired
    ContactsRepository repository;
    @Autowired
    private EmailSender emailSender;
//    @Autowired
//    private SmsSender smsSender;

    public void send(Message message) {
        List<Contacts> list = repository.findAllByUserId(message.getUserId());
        setStatusAndSave(list, MailingStatus.IN_PROCESS);

        list.forEach(el -> sendSingleMessage(el, message.getMessage()));
    }

    private void setStatusAndSave(List<Contacts> list, MailingStatus status) {
        list.forEach(el -> el.setMailingStatus(status));
        repository.saveAll(list);
    }

    private void sendSingleMessage(Contacts contact, String text) {
        String personContact = contact.getUserContact();
        switch (contact.getContactType()) {
            case Email -> emailSender.send(personContact, text);
            //case Sms -> ...
        }
        setStatusAndSave(List.of(contact), MailingStatus.DONE);
    }


}