package ru.micro.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.micro.dto.MessageRequest;
import ru.micro.service.MessageService;


@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    MessageService msgService;
    @PostMapping()
    public void addToKafka(@RequestHeader("id") int userId,
                     @RequestBody @Valid MessageRequest msg) {
        msgService.sendMessage(userId, msg.getArticle(), msg.getMessage());
    }

}
