package com.katzendorn.demo.repository;

import com.katzendorn.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    // OR JpaRepository<User, Long>
    User findByUsername(String username);
    List<User> findAll();
    void deleteById(Long id);

    Optional<User> findById(Long id);

}
