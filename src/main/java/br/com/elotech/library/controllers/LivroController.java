package br.com.elotech.library.controllers;

import br.com.elotech.library.models.Livro;
import br.com.elotech.library.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {
    private final LivroService livroService;

    @Autowired
    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Livro> criarLivro(@RequestBody Livro livro) {
        return ResponseEntity.ok(this.livroService.criarLivro(livro));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Livro>> getTodosLivros(){
        return ResponseEntity.ok(this.livroService.getTodosLivros());
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Livro> atualizarLivro(@RequestBody Livro livro) {
        return ResponseEntity.ok(this.livroService.atualizarLivro(livro));
    }

    @GetMapping("/{livroId}")
    public ResponseEntity<Livro> buscarUsuarioPorId(@PathVariable Long livroId) {
        return ResponseEntity.ok(this.livroService.buscarUsuarioPorId(livroId));
    }

    @DeleteMapping("/{livroId}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long livroId) {
        this.livroService.deletarLivro(livroId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recomendacoes/{usuarioId}")
    public ResponseEntity<List<Livro>> buscarRecomendacoesParaUsuario(@PathVariable Long usuarioId){
        return ResponseEntity.ok(this.livroService.buscarRecomendacoesParaUsuario(usuarioId));
    }

    @GetMapping("/buscar-livros-google-books")
    public ResponseEntity<List<Livro>> buscarLivrosGoogleBooks(@RequestParam(value = "nomeLivro") String nomeLivro){
        return ResponseEntity.ok(this.livroService.buscarLivrosGoogleBooks(nomeLivro));
    }
}
