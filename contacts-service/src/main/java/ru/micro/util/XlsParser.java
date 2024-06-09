package ru.micro.util;

import com.sun.jdi.InternalException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.micro.entity.Contacts;
import ru.micro.model.MailingStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class XlsParser {
    private Workbook read(MultipartFile file) {
        try (InputStream fileInputStream = file.getInputStream()) {
            return new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            throw new InternalException("Ошибка при чтении файла Excel");
        }
    }

    private Map<String, String> getRows(Workbook workbook) throws IOException {
        Sheet sheet = workbook.getSheetAt(0);
        Map<String, String> data = new HashMap<>();
        for (Row row : sheet) {
            data.put(row.getCell(0).toString(), row.getCell(1).toString());
        }
        workbook.close();
        return data;
    }

    public List<Contacts> getContacts(int ownerId, MultipartFile file) throws IOException {
        Map<String, String> result = getRows(read(file));
        List<Contacts> contacts = new ArrayList<>();
        for (Map.Entry<String, String> pair : result.entrySet()) {
            Contacts contact = new Contacts();
            contact.setId(UUID.randomUUID());
            contact.setUserId(ownerId);
            contact.setUserName(pair.getKey());
            contact.setUserContact(pair.getValue());
            contact.setMailingStatus(MailingStatus.NOT_STARTED);
            contacts.add(contact);
        }
        return contacts;
    }





}
