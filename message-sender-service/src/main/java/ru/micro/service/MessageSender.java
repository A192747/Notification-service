package ru.micro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.micro.dto.Message;
import ru.micro.entity.Contacts;
import ru.micro.model.MailingStatus;
import ru.micro.repository.ContactsRepository;

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

        /* TODO
         Добавить обработку при перезапуске.
         Чтобы после неотправленные сообщения доотправились.
         */

        List<Contacts> list;
        if(message.getUserName() != null) {
            list  = List.of(repository.findByUserNameAndUserId(message.getUserName(), message.getUserId()));
        } else {
            list = repository.findAllByUserId(message.getUserId());
        }

        setStatusAndSave(list, MailingStatus.IN_PROCESS);

        list.forEach(el -> sendSingleMessage(el, message));
        setStatusAndSave(list, MailingStatus.NOT_STARTED);
    }

    private void setStatusAndSave(List<Contacts> list, MailingStatus status) {
        list.forEach(el -> el.setMailingStatus(status));
        repository.saveAll(list);
    }

    private void sendSingleMessage(Contacts contact, Message msg) {
        String personContact = contact.getUserContact();
        String article = msg.getArticle();
        String text = msg.getMessage();
        switch (contact.getContactType()) {
            case Email -> emailSender.send(personContact, article, text);
            //case Sms -> ...
        }
        setStatusAndSave(List.of(contact), MailingStatus.DONE);
    }


}
