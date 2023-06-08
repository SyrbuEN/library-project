package ru.itgirl.libraryproject.service;

import ru.itgirl.libraryproject.dto.AuthorDto;
import ru.itgirl.libraryproject.dto.BookDto;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);

    AuthorDto getByNameV1(String name);

    AuthorDto getByNameV2(String name);

    AuthorDto getByNameV3(String name);
}
