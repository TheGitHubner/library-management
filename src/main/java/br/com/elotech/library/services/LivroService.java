package br.com.elotech.library.services;

import br.com.elotech.library.models.Livro;
import br.com.elotech.library.repositories.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class LivroService {
    private static final String GOOGLE_BOOKS_API = "https://www.googleapis.com/books/v1/volumes?q=";

    private final LivroRepository livroRepository;
    private final EmprestimoService emprestimoService;

    @Autowired
    public LivroService(LivroRepository livroRepository,
                        EmprestimoService emprestimoService) {
        this.livroRepository = livroRepository;
        this.emprestimoService = emprestimoService;
    }

    public List<Livro> getTodosLivros() {
        return livroRepository.findAll();
    }

    @Transactional
    public Livro criarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    @Transactional
    public Livro atualizarLivro(Livro livro) {
        if (!this.livroRepository.existsById(livro.getId())) {
            throw new RuntimeException("Livro não encontrado para realizar atualização.");
        }
        return livroRepository.save(livro);
    }

    @Transactional
    public void deletarLivro(Long livroId) {
        if (this.livroRepository.existsById(livroId)) {
            if (this.emprestimoService.existeEmprestimoPendenteByLivroId(livroId)) {
                throw new RuntimeException("Livro não pode ser excluído pois existem cópias dele emprestadas.");
            }
            this.emprestimoService.deletarEmprestimosByLivroId(livroId);
            this.livroRepository.deleteById(livroId);
        }
    }

    public List<Livro> buscarLivrosGoogleBooks(String nomeLivro) {
        if(nomeLivro.isBlank()) return new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        List<Livro> livros = new ArrayList<>();

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    GOOGLE_BOOKS_API + nomeLivro,
                    HttpMethod.GET,
                    httpEntity,
                    Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseBody = response.getBody();
                if (Objects.nonNull(responseBody)) {
                    List<Map<String, Object>> books = (List<Map<String, Object>>) responseBody.get("items");
                    livros = this.montarListaLivros(books);
                }
            }

            return livros;
        } catch (RestClientException e) {
            throw new RuntimeException("Não foi possível se comunicar com a API do Google Books.");
        }
    }

    private List<Livro> montarListaLivros(List<Map<String, Object>> books) {
        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> book : books) {
            Map<String, Object> volumeInfo = (Map<String, Object>) book.get("volumeInfo");

            String titulo = (String) volumeInfo.get("title");
            List<String> autores = (List<String>) volumeInfo.get("authors");
            List<String> categorias = (List<String>) volumeInfo.get("categories");
            LocalDate dataPublicacao = Objects.nonNull(volumeInfo.get("publishedDate"))
                ? this.pegarDataPublicacao((String) volumeInfo.get("publishedDate"))
                : LocalDate.now();

            List<Map<String, String>> industryIdentifiers = (List<Map<String, String>>) volumeInfo.get("industryIdentifiers");

            List<String> isbnList = new ArrayList<>();
            if (Objects.nonNull(industryIdentifiers)) {
                for (Map<String, String> identifier : industryIdentifiers) {
                    isbnList.add(identifier.get("identifier"));
                }
            }

            Livro livro = new Livro();
            livro.setTitulo(titulo);
            livro.setAutor(Objects.nonNull(autores) ? String.join(", ", autores) : "");
            livro.setCategoria(Objects.nonNull(categorias) ? String.join(", ", categorias) : "");
            livro.setIsbn(String.join(", ", isbnList));
            livro.setDataPublicacao(dataPublicacao);

            livros.add(livro);
        }
        return livros;
    }

    private LocalDate pegarDataPublicacao(String publishedDate) {
        try {
            return LocalDate.parse(publishedDate);
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(publishedDate.concat("-01-01"));
            } catch (DateTimeParseException e2) {
                return LocalDate.now();
            }
        }
    }

    public List<Livro> buscarRecomendacoesParaUsuario(Long usuarioId) {
        return this.livroRepository.buscarRecomendacoesParaUsuario(usuarioId);
    }

    public Livro buscarLivroPorId(Long livroId) {
        return this.livroRepository.findById(livroId)
                .orElse(null);
    }
}
