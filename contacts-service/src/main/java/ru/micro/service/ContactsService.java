package ru.micro.service;

import com.sun.jdi.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.micro.dto.ContactRequest;
import ru.micro.dto.DeleteRequest;
import ru.micro.entity.Contacts;
import ru.micro.exceptions.NotValidException;
import ru.micro.repository.ContactsRepository;
import ru.micro.util.ContactTypeDetector;
import ru.micro.util.XlsParser;

import java.io.IOException;
import java.util.*;

@Service
public class ContactsService {
    @Autowired
    XlsParser xlsParser;
    @Autowired
    ContactsRepository repository;

    public void saveInfo(int userId, MultipartFile file) {
        try {
            List<Contacts> list = xlsParser.getContacts(userId, file);
            list.forEach(el -> el.setContactType(
                    ContactTypeDetector.detectType(el.getUserContact())
            ));

            List <Contacts> result = new LinkedList<>();

            //проверим есть ли уже такие пользователи в бд
            fillResult(userId, list, result);

            repository.saveAll(result);
        } catch (IOException exception) {
            throw new InternalException("Не удалось прочитать загружаемый файл");
        } catch (NotValidException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new InternalException("Не удалось сохранить информацию в базу данных");
        }
    }

    private void fillResult(int userId, List<Contacts> list, List<Contacts> result) {
        Set<String> uniqueNames = new HashSet<>();
        for (Contacts el: list) {
            String name = el.getUserName();
            System.out.println(uniqueNames);
            if (uniqueNames.contains(name))
                throw new NotValidException("В файле есть повторяющиеся имена. Измените их, чтобы у каждого контакта было уникальноен имя");
            uniqueNames.add(name);
            Contacts cont = repository.findByUserNameAndUserId(el.getUserName(), userId);
            if (cont != null) {
                cont.setUserContact(el.getUserContact());
                result.add(cont);
            } else {
                result.add(el);
            }
        }
        ;
    }

    public void deleteInfo(int userId, List<DeleteRequest> list) {
        List<String> namesInRemoveList = list.stream()
                .map(DeleteRequest::getName)
                .toList();

        List<Contacts> contacts = namesInRemoveList.stream()
                .map(el -> repository.findByUserNameAndUserId(el, userId))
                .toList();

        repository.deleteAll(contacts);
    }

    public void updateInfo(int userId, List<ContactRequest> list) {
        Map<String, String> map = new HashMap<>();
        list.forEach(el -> map.put(el.getUserName(), el.getContact()));

        List<String> namesInList = map.keySet().stream().toList();
        List<Contacts> result = new LinkedList<>();
        namesInList.forEach(el -> {
            Contacts cont = repository.findByUserNameAndUserId(el, userId);
            if (cont != null) {
                cont.setUserContact(map.get(el));
                result.add(cont);
            }
        });

        repository.saveAll(result);
    }

    public List<Contacts> getAllContacts(int userId) {
        return repository.findAllByUserId(userId);
    }



}
