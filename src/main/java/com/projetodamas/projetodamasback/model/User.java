package com.projetodamas.projetodamasback.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = ("tb_user"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String email;
    private String cpf;
    private String senha;
    private String telefone;

}
