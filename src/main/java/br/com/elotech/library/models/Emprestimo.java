package br.com.elotech.library.models;

import br.com.elotech.library.models.enums.StatusEmprestimo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@Table(name = "emprestimo")
@NoArgsConstructor
@AllArgsConstructor
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @NotNull(message = "Identificação do usuário não pode ser nulo")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    @NotNull(message = "Identificação do livro não pode ser nulo")
    private Livro livro;

    @Column(name = "data_emprestimo")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Data do empréstimo não pode ser nulo")
    private LocalDate dataEmprestimo;

    @Column(name = "data_devolucao")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Data da devolução não pode ser nulo")
    private LocalDate dataDevolucao;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status não pode ser nulo")
    private StatusEmprestimo status;
}