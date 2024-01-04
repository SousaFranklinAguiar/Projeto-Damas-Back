package com.projetodamas.projetodamasback.service;

import com.projetodamas.projetodamasback.exceptions.ErrorException;
import com.projetodamas.projetodamasback.model.User;
import com.projetodamas.projetodamasback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        try {
            String hashedPassword = new BCryptPasswordEncoder().encode(user.getSenha());
            user.setSenha(hashedPassword);
            userRepository.save(user);

        } catch (Exception e) {
            throw new ErrorException("Erro ao cadastrar usuário.");
        }
    }

    public ResponseEntity<?> login(User user){
        try {
            return realizarLogin(user);
        } catch (Exception e) {
            throw new ErrorException("Erro ao realizar login.");
        }
    }

    public void verificaCpf(User user){
        User userSalvo = userRepository.findByCpf(user.getCpf());
        if(!(userSalvo == null)){
            throw new ErrorException("CPF já cadastrado.");
        }
    }

    public void verificaEmail(User user){
        User userSalvo = userRepository.findByEmail(user.getEmail());
        if(!(userSalvo == null)){
            throw new ErrorException("E-mail já cadastrado.");
        }
    }

    public ResponseEntity<?> realizarLogin(User user){
        User userSalvo = userRepository.findByCpf(user.getCpf());

        if(userSalvo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não cadastrado.");
        }

        if (!new BCryptPasswordEncoder().matches(user.getSenha(), userSalvo.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta.");
        }

        userSalvo.setSenha("");

        return ResponseEntity.status(HttpStatus.OK).body(userSalvo);
    }
}
