package ru.micro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.micro.model.ContactType;

@Data
@NoArgsConstructor
@Setter
public class ContactRequest {
    private String userName;
    private String contact;
}
