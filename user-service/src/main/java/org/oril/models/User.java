package org.oril.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Size(min = 1)
    @NotEmpty(message = "Имя не может быть пустым!")
    private String name;
    @NotNull
    @Size(min = 1)
    @NotEmpty(message = "Поле для пароля не может быть пустым!")
    private String password;
    @NotNull(message = "Роль не может быть пустой!")
    @Enumerated(EnumType.STRING)
    private Roles role;
}

