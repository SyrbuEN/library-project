package ru.itgirl.libraryproject.model;

import jakarta.persistence.*;
import lombok.*;
import ru.itgirl.libraryproject.dto.UsersDto;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Setter
    private String user_name;

    @Column(nullable = false)
    @Setter
    private String user_password;

    @Column(nullable = false)
    @Setter
    private String user_role;

}
