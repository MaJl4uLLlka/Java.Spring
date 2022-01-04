package com.example.mainspingproject.repository;

import com.example.mainspingproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Collection<User> findByLastName(String lastName);
    Optional<User> findByEmail(String email);

    @Query(value = "insert into users (email, password, first_name, last_name) values (:email, :password, :firstName, :lastName)", nativeQuery = true)
    void insertUser(@Param("email") String email, @Param("password") String password,
                    @Param("firstName") String firstName, @Param("lastName") String lastName);
}
