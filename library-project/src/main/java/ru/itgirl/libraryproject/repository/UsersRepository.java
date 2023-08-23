package ru.itgirl.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.itgirl.libraryproject.model.Users;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {
    @Query(nativeQuery = true, value = "SELECT * FROM USERS WHERE user_name = ?1 and user_password = ?2")
    List<Users> findUserByNameAndPassword(String user_name, String password);
}
