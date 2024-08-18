package br.com.elotech.library.controllers;


import br.com.elotech.library.models.Usuario;
import br.com.elotech.library.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(this.usuarioService.criarUsuario(usuario));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Usuario>> getTodosUsuarios(){
        return ResponseEntity.ok(this.usuarioService.getTodosUsuarios());
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(this.usuarioService.atualizarUsuario(usuario));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(this.usuarioService.buscarUsuarioPorId(usuarioId));
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long usuarioId) {
        this.usuarioService.deletarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
