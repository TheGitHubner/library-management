package br.com.elotech.library.services;

import br.com.elotech.library.models.Emprestimo;
import br.com.elotech.library.models.Livro;
import br.com.elotech.library.models.Usuario;
import br.com.elotech.library.models.dtos.EmprestimoDTO;
import br.com.elotech.library.repositories.EmprestimoRepository;
import br.com.elotech.library.repositories.LivroRepository;
import br.com.elotech.library.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    @Autowired
    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             UsuarioRepository usuarioRepository,
                             LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public Emprestimo criarEmprestimo(EmprestimoDTO emprestimoDTO) {
        if (this.emprestimoRepository.existeEmprestimoPendenteByLivroId(emprestimoDTO.getLivroId())){
            throw new RuntimeException("Não foi possível realizar o empréstimo pois o livro já se encontra emprestado.");
        } else if (emprestimoDTO.getDataEmprestimo().isAfter(LocalDate.now()) ||
                emprestimoDTO.getDataEmprestimo().isAfter(emprestimoDTO.getDataDevolucao())) {
            throw new RuntimeException("Data do empréstimo não pode ser superior a data atual e deve ser menor que a data prevista da devolução.");
        }

        Usuario usuario = this.usuarioRepository.findById(emprestimoDTO.getUsuarioId())
                .orElseThrow(()-> new RuntimeException("Usuário vinculado ao empréstimo não foi encontrado"));

        Livro livro = this.livroRepository.findById(emprestimoDTO.getLivroId())
                .orElseThrow(()-> new RuntimeException("Livro vinculado ao empréstimo não foi encontrado"));

        return this.emprestimoRepository.save(
            Emprestimo
                .builder()
                .usuario(usuario)
                .livro(livro)
                .dataEmprestimo(emprestimoDTO.getDataEmprestimo())
                .dataDevolucao(emprestimoDTO.getDataDevolucao())
                .status(emprestimoDTO.getStatus())
                .build()
        );
    }

    public List<Emprestimo> getTodosEmprestimos() {
        return this.emprestimoRepository.findAll();
    }

    @Transactional
    public Emprestimo atualizarEmprestimo(Emprestimo emp) {
        Optional<Emprestimo> emprestimo = this.emprestimoRepository.findById(emp.getId());
        if (emprestimo.isPresent()) {
            emprestimo.get().setDataDevolucao(emp.getDataDevolucao());
            emprestimo.get().setStatus(emp.getStatus());
            return this.emprestimoRepository.save(emprestimo.get());
        } else {
            throw new RuntimeException("Empréstimo não encontrado para realizar atualização.");
        }
    }

    @Transactional
    public void deletarEmprestimosByUsuarioId(Long usuarioId) {
        if (this.emprestimoRepository.existsByUsuarioId(usuarioId))
            this.emprestimoRepository.deletarEmprestimosByUsuarioId(usuarioId);
    }

    public boolean existeEmprestimoPendenteByUsuarioId(Long usuarioId) {
        return this.emprestimoRepository.existeEmprestimoPendenteByUsuarioId(usuarioId);
    }

    @Transactional
    public void deletarEmprestimosByLivroId(Long livroId) {
        this.emprestimoRepository.deletarEmprestimosByLivroId(livroId);
    }

    public boolean existeEmprestimoPendenteByLivroId(Long livroId) {
        return this.emprestimoRepository.existeEmprestimoPendenteByLivroId(livroId);
    }

    public Emprestimo buscarEmprestimoPorId(Long emprestimoId) {
        return this.emprestimoRepository.findById(emprestimoId)
                .orElse(null);
    }
}
