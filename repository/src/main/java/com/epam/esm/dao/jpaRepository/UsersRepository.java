package com.epam.esm.dao.jpaRepository;

import com.epam.esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    /**
     * Method for read user by name
     *
     * @param name
     * @return User
     */
    Optional<User> readByName(String name);
}