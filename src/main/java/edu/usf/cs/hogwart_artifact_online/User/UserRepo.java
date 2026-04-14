package edu.usf.cs.hogwart_artifact_online.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByUserName(String username); // derive query method
    }
