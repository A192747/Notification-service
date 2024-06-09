package ru.micro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.micro.service.SaveService;


@RestController
@RequestMapping("/upload")
public class ContactsController {
    @Autowired
    SaveService saveService;

    @PostMapping()
    public void save(@RequestHeader("id") int userId,
                     @RequestParam("file") MultipartFile file) {
        saveService.saveInfo(userId, file);
    }

}
