package br.com.elotech.library.repositories;

import br.com.elotech.library.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    @Query(nativeQuery = true,
            value = " WITH categorias_recomendadas AS " +
                    " ( " +
                    "   SELECT  " +
                    "     l.categoria " +
                    "   FROM emprestimo e  " +
                    "     JOIN livro l " +
                    "       ON l.id = e.livro_id " +
                    "   WHERE e.usuario_id = :usuarioId " +
                    " ) " +
                    " SELECT  " +
                    "   l.* " +
                    " FROM livro l " +
                    "   JOIN categorias_recomendadas cr  " +
                    "     ON l.categoria LIKE cr.categoria ")
    List<Livro> buscarRecomendacoesParaUsuario(@Param("usuarioId") Long usuarioId);
}
