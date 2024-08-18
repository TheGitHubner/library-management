package br.com.elotech.library.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "livro")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Título não pode ser nulo")
    private String titulo;

    @NotNull(message = "Autor não pode ser nulo")
    private String autor;

    @NotNull(message = "ISBN não pode ser nulo")
    private String isbn;

    @Column(name = "data_publicacao")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Data da publicação não pode ser nulo")
    private LocalDate dataPublicacao;

    @NotNull(message = "Categoria não pode ser nulo")
    private String categoria;
}
