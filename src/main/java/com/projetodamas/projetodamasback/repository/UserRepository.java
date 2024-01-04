package com.projetodamas.projetodamasback.repository;

import com.projetodamas.projetodamasback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByCpf(String cpf);
    User findByEmail(String email);
}
