package br.com.elotech.library.services;

import br.com.elotech.library.models.Usuario;
import br.com.elotech.library.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmprestimoService emprestimoService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,
                          EmprestimoService emprestimoService) {
        this.usuarioRepository = usuarioRepository;
        this.emprestimoService = emprestimoService;
    }

    public List<Usuario> getTodosUsuarios() {
        return this.usuarioRepository.findAll();
    }

    @Transactional
    public Usuario criarUsuario(Usuario usuario) {
        if (usuario.getDataCadastro().isAfter(LocalDate.now())){
            throw new RuntimeException("Data de cadastro do usuário não pode ser superior a data atual");
        }
        return this.usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizarUsuario(Usuario usuario) {
        if (!this.usuarioRepository.existsById(usuario.getId())) {
            throw new RuntimeException("Usuário não encontrado para realizar atualização.");
        }
        return this.usuarioRepository.save(usuario);
    }

    @Transactional
    public void deletarUsuario(Long usuarioId) {
        if (!this.usuarioRepository.existsById(usuarioId)) {
            throw new RuntimeException("Usuário não encontrado para realizar exclusão.");
        }

        if (this.emprestimoService.existeEmprestimoPendenteByUsuarioId(usuarioId)) {
            throw new RuntimeException("Usuário não pode ser excluído pois ainda possui empréstimos em aberto.");
        }

        this.emprestimoService.deletarEmprestimosByUsuarioId(usuarioId);
        this.usuarioRepository.deleteById(usuarioId);
    }

    public Usuario buscarUsuarioPorId(Long usuarioId) {
        return this.usuarioRepository.findById(usuarioId)
                .orElse(null);

    }
}
