package br.com.elotech.library.models.dtos;

import br.com.elotech.library.models.enums.StatusEmprestimo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmprestimoDTO {
    private Long usuarioId;
    private Long livroId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataEmprestimo;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDevolucao;
    private StatusEmprestimo status;
}

