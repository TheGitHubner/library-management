package br.com.elotech.library.controllers;

import br.com.elotech.library.models.Emprestimo;
import br.com.elotech.library.services.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
    private final EmprestimoService emprestimoService;

    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Emprestimo> criarEmprestimo(@RequestBody Emprestimo emprestimo) {
        return ResponseEntity.ok(this.emprestimoService.criarEmprestimo(emprestimo));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Emprestimo>> getTodosEmprestimos(){
        return ResponseEntity.ok(this.emprestimoService.getTodosEmprestimos());
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Emprestimo> atualizarEmprestimo(@RequestBody Emprestimo emprestimo) {
        return ResponseEntity.ok(this.emprestimoService.atualizarEmprestimo(emprestimo));
    }
}
