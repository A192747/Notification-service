package ru.micro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.micro.dto.ContactResponse;
import ru.micro.entity.Contacts;
import ru.micro.service.ContactsService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/contacts")
public class ContactsController {
    @Autowired
    ContactsService contactsService;

    @PostMapping()
    public void save(@RequestHeader("id") int userId,
                     @RequestParam("file") MultipartFile file) {
        contactsService.saveInfo(userId, file);
    }

    @GetMapping()
    public ResponseEntity<List<ContactResponse>> getContacts(@RequestHeader("id") int userId) {
        return ResponseEntity.ok(
                convertToResponseList(contactsService.getAllContacts(userId))
        );
    }

    private List<ContactResponse> convertToResponseList(List<Contacts> list) {
        List<ContactResponse> newList = new ArrayList<>();
        list.forEach(el -> newList.add(convertToResponse(el)));
        return newList;
    }
    private ContactResponse convertToResponse(Contacts contacts) {
        ContactResponse response = new ContactResponse();
        response.setUserName(contacts.getUserName());
        response.setContact(contacts.getUserContact());
        response.setType(contacts.getContactType());
        return response;
    }

}
