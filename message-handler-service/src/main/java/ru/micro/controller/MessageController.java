package ru.micro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.micro.service.MessageService;


@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    MessageService msgService;
    @PostMapping()
    public void addToKafka(@RequestHeader("id") int userId,
                     @RequestBody String text) {
        msgService.sendMessage(userId, text);
    }

}
