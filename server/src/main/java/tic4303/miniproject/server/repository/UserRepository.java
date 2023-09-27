package tic4303.miniproject.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tic4303.miniproject.server.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    // email is the username, which is the unique field
    Optional<User> findByEmail(String email);

}
