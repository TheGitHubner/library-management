package br.com.elotech.library.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nome não pode ser nulo")
    private String nome;

    @Email(message = "Informe um email válido")
    @NotNull(message = "Email não pode ser nulo")
    private String email;

    @Column(name = "data_cadastro")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Data de cadastro não pode ser nulo")
    private LocalDate dataCadastro;

    @NotNull(message = "Telefone não pode ser nulo")
    private String telefone;
}