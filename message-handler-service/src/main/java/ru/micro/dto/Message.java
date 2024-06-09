package ru.micro.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Message {
    private int userId;
    private String article;
    private String message;
}
