package ru.micro.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.micro.model.ContactType;

@Data
@NoArgsConstructor
@Setter
public class ContactResponse {
    private String userName;
    private String contact;
    private ContactType type;
}
