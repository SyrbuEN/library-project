package ru.itgirl.libraryproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.dto.UsersDto;
import ru.itgirl.libraryproject.model.Users;
import ru.itgirl.libraryproject.repository.UsersRepository;
import ru.itgirl.libraryproject.service.UsersService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Override
    public UsersDto getUserRoles(String name, String password) {
        List<Users> users = usersRepository.findUserByNameAndPassword(name, password);

        List<String> roles = new ArrayList<>();
        for (Users user: users) {
            roles.add(user.getUser_role());
        }

        UsersDto newUser = new UsersDto();
        newUser.setUser_name(users.get(0).getUser_name());
        newUser.setUser_password(users.get(0).getUser_password());
        newUser.setUser_roles(roles);

        return newUser;
    }
}
