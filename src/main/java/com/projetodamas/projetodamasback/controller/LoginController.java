package com.projetodamas.projetodamasback.controller;


import com.projetodamas.projetodamasback.exceptions.ErrorException;
import com.projetodamas.projetodamasback.model.User;
import com.projetodamas.projetodamasback.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "http://localhost:8080/")
@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value="/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try {
            return userService.login(user);
        } catch (ErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value="/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public String signin(@RequestBody User user){

        try {
            // Verificar se o CPF existe
            userService.verificaValidadeCpf(user);
            // Verificar se o CPF já está cadastrado
            userService.verificaCpf(user);
            // Verificar se o email já está cadastrado
            userService.verificaEmail(user);
            // Lógica de cadastro de usuário
            userService.createUser(user);

            return "Usuário cadastrado com sucesso!";
        } catch (ErrorException e) {
            return e.getMessage();
        }
    }

}
