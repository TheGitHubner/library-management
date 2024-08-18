package br.com.elotech.library.services;

import br.com.elotech.library.models.Emprestimo;
import br.com.elotech.library.repositories.EmprestimoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;

    @Autowired
    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    @Transactional
    public Emprestimo criarEmprestimo(Emprestimo emprestimo) {
        if (this.emprestimoRepository.existeEmprestimoPendenteByLivroId(emprestimo.getLivro().getId())){
            throw new RuntimeException("Não foi possível realizar o empréstimo pois o livro já se encontra emprestado.");
        } else if (emprestimo.getDataEmprestimo().isAfter(LocalDate.now()) ||
                   emprestimo.getDataEmprestimo().isAfter(emprestimo.getDataDevolucao())) {
            throw new RuntimeException("Data do empréstimo não pode ser superior a data atual e deve ser menor que a data prevista da devolução.");
        }
        return this.emprestimoRepository.save(emprestimo);
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
}
