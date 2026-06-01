package com.pbl.IDE.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pbl.IDE.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
