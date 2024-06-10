package ru.micro.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MessageRequest {
    private String userName;
    @NotNull
    @NotEmpty(message = "Тема сообщения должна быть указана")
    @Size(min = 1)
    private String article;
    @NotNull
    @NotEmpty(message = "Текст сообщения не должен быть пустым")
    @Size(min = 1)
    private String message;
}
