package ru.itgirl.libraryproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itgirl.libraryproject.model.Users;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsersDto {
    private String user_name;
    private String user_password;
    private List<String> user_roles;
}
