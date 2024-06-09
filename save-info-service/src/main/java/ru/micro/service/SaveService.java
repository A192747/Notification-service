package ru.micro.service;

import com.sun.jdi.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.micro.entity.Contacts;
import ru.micro.repository.ContactsRepository;
import ru.micro.util.XlsParser;

import java.io.IOException;
import java.util.List;

@Service
public class SaveService {
    @Autowired
    XlsParser xlsParser;
    @Autowired
    ContactsRepository repository;

//    @Transactional
    public void saveInfo(int userId, MultipartFile file) {
        try {
            List<Contacts> list = xlsParser.getContacts(userId, file);
            repository.saveAll(list);
        } catch (IOException exception) {
            throw new InternalException("Не удалось прочитать загружаемый файл");
        } catch (Exception exception) {
            throw new InternalException("Не удалось сохранить информацию в базу данных");
        }
    }
}
