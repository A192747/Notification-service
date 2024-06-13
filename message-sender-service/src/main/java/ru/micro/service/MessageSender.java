package ru.micro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.micro.dto.Message;
import ru.micro.entity.Contacts;
import ru.micro.model.MailingStatus;
import ru.micro.repository.ContactsRepository;
import ru.micro.service.senders.EmailSender;

import java.util.List;

@Service
public class MessageSender {
    @Autowired
    ContactsRepository repository;
    @Autowired
    private EmailSender emailSender;

    @Value("${mailing.send}")
    private Boolean sendMessages;
//    @Autowired
//    private SmsSender smsSender;

    public void send(Message message) {

        /* TODO
         Добавить обработку при перезапуске.
         Чтобы после неотправленные сообщения доотправились.
         */

        List<Contacts> list;
        int userId = message.getUserId();
        String userName = message.getUserName();

        if (userName != null) {
            list = List.of(repository.findByUserNameAndUserId(userName, userId));
        } else {
            list = repository.findAllByUserId(userId);
        }

        //для того, чтобы доотправить сообщения после перезапуска
        List<Contacts> inProcess = list.stream().filter(el -> el.getMailingStatus().equals(MailingStatus.IN_PROCESS)).toList();
        if (!inProcess.isEmpty())
            list = inProcess;
        else
            setStatusAndSave(list, MailingStatus.IN_PROCESS);
        if(sendMessages)
            list.forEach(el -> sendMessage(el, message));
        setStatusAndSave(list, MailingStatus.NOT_STARTED);
    }

    private void setStatusAndSave(List<Contacts> list, MailingStatus status) {
        list.forEach(el -> el.setMailingStatus(status));
        repository.saveAll(list);
    }

    private void sendMessage(Contacts contact, Message msg) {
        String personContact = contact.getUserContact();
        String article = msg.getArticle();
        String text = msg.getMessage();
        switch (contact.getContactType()) {
            case Email -> emailSender.send(personContact, article, text);
            //case Sms -> ...
        }
//        System.out.println("DONE sending" + msg);
        setStatusAndSave(List.of(contact), MailingStatus.DONE);
    }


}
