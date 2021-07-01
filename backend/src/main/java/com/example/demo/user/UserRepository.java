package com.example.demo.user;

import com.example.demo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query( value = "select * from user inner join user_roles on user.id = user_roles.user_id inner join role on user_roles.role_id = role.id where role.name = 'CUSTOMER'", nativeQuery = true )
    List<User> allCustomers();

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}

