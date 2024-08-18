package br.com.elotech.library.repositories;

import br.com.elotech.library.models.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    @Query(nativeQuery = true,
            value = " SELECT " +
                    "   COUNT(*) > 0 " +
                    " FROM emprestimo e " +
                    " WHERE e.usuario_id = :usuarioId " +
                    "   AND e.status = 'RETIRADO' ")
    boolean existeEmprestimoPendenteByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query(nativeQuery = true,
            value = " SELECT " +
                    "   COUNT(*) > 0 " +
                    " FROM emprestimo e " +
                    " WHERE e.livro_id = :livroId " +
                    "   AND e.status = 'RETIRADO' ")
    boolean existeEmprestimoPendenteByLivroId(@Param("livroId") Long livroId);

    @Modifying
    @Query(nativeQuery = true,
            value = " DELETE FROM emprestimo " +
                    " WHERE usuario_id = :usuarioId ")
    void deletarEmprestimosByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Modifying
    @Query(nativeQuery = true,
            value = " DELETE FROM emprestimo " +
                    " WHERE livro_id = :livroId ")
    void deletarEmprestimosByLivroId(@Param("livroId") Long livroId);

    boolean existsByUsuarioId(Long usuarioId);
}
