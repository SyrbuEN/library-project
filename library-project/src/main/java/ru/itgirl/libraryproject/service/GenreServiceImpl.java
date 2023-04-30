package ru.itgirl.libraryproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.dto.*;
import ru.itgirl.libraryproject.model.Author;
import ru.itgirl.libraryproject.model.Genre;
import ru.itgirl.libraryproject.repository.AuthorRepository;
import ru.itgirl.libraryproject.repository.GenreRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    private DataSource dataSource;

    @Override
    public GenreDto getGenreById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        GenreDto genreDto = convertEntityToDto(genre);
        return genreDto;
    }

    private GenreDto convertEntityToDto(Genre genre) {
        Long idGenre = genre.getId();

        List<BookWithAuthorDto> bookWithAuthorDtoList = new ArrayList<>();
        String SQL_findAllBooks = "Select * from book where genre_id = " + idGenre +";";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_findAllBooks)) {
            while (resultSet.next()){
                BookWithAuthorDto bookWithAuthorDto = GetBookWithAuthorByGenre(resultSet);
                bookWithAuthorDtoList.add(bookWithAuthorDto);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        GenreDto genreDto = GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .books(bookWithAuthorDtoList)
                .build();

        return genreDto;
    }

    private BookWithAuthorDto GetBookWithAuthorByGenre(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");

        List <String> authors = new ArrayList<>();
        String SQL_findAllAuthors = "select * from author_book where book_id = " + id +";";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSetAuthor = statement.executeQuery(SQL_findAllAuthors)) {
            while (resultSetAuthor.next()){
                  authors.add(GetAuthorByBook(resultSetAuthor));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return new BookWithAuthorDto(id, name, authors);
    }

    private String GetAuthorByBook(ResultSet resultSetAuthor) throws SQLException {
        Long author_id = resultSetAuthor.getLong("author_id");
        Author author = authorRepository.findById(author_id).orElseThrow();
        return author.getName() + " " + author.getSurname();
    }
}
