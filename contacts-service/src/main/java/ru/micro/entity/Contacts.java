package ru.micro.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import ru.micro.model.ContactType;
import ru.micro.model.MailingStatus;

import java.util.UUID;

@Table("contacts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Contacts {
    @PrimaryKey
    @Column("id")
    private UUID id;

    @Column("user_id")
    @NotNull
    private Integer userId;

    @Column("user_name")
    @NotNull
    @Size(min = 1)
    @NotEmpty(message = "Имя не может быть пустым!")
    private String userName;

    @Column("user_contact")
    @NotNull
    @Size(min = 1)
    @NotEmpty(message = "Информация о контактах пользователя не может быть пустой!")
    private String userContact;

    @Column("mailing_status")
    @NotNull
//    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Информация о контактах пользователя не может быть пустой!")
    private MailingStatus mailingStatus;

    @Column("contact_type")
    @NotNull
    @NotEmpty(message = "Информация о контактах пользователя не может быть пустой!")
    private ContactType contactType;
}

