package ru.itgirl.libraryproject.service;

import ru.itgirl.libraryproject.dto.UsersDto;

public interface UsersService {
   UsersDto getUserRoles(String name, String password);
}
