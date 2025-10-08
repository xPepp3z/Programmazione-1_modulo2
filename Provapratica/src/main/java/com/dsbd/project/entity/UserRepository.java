package com.dsbd.project.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    // Metodo custom per cercare un utente tramite email
    User findByEmail(String email);
}
