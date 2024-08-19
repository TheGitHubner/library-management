# Gerenciador de biblioteca

## Requisitos
* Docker
* JDK

## Passo-a-passo para execução
* Clonar o repositório:
  * `https://github.com/TheGitHubner/library-management.git`
* Abrir um terminal na raíz do projeto e executar o comando:
  *  `docker-compose up`
* URL para as requisições:
  * `http://localhost:8080` 

## End-points

### Usuário
* Criação de usuário [POST]: `/usuarios`
  * Corpo da requisição
   ```
    {
        "nome": "Emily",
        "email": "teste@gmail.com",
        "dataCadastro": "20/02/2022",
        "telefone": "44999802514"
    }
    ```
   * Resposta
    ```
    {
        "id": 1,
        "nome": "Emily",
        "email": "teste@gmail.com",
        "dataCadastro": "20/02/2022",
        "telefone": "44999802514"
    }
    ```
   * Observações:
     * Data deve ser enviada no formato "DD/MM/AAAA"
 
 * Buscar todos os usuários [GET]: `/usuarios`
   * Resposta
   ```
   [
      {
          "id": 1,
          "nome": "Emily",
          "email": "teste@gmail.com",
          "dataCadastro": "20/02/2022",
          "telefone": "44999802514"
      }
   ]
   ```
   
* Excluir usuário [DELETE]: `/usuarios/1`
   * Resposta
     * Será apagado o usuário com ID 1 e voltará o status 204 (No Content)

* Edição de usuário [PUT]: `/usuarios/atualizar`
  * Corpo da requisição
   ```
    {
        "id": 1,
        "nome": "Emily",
        "email": "teste@gmail.com",
        "dataCadastro": "20/02/2022",
        "telefone": "44999802514"
    }
    ```
   * Resposta
    ```
    {
        "id": 1,
        "nome": "Emily",
        "email": "teste@gmail.com",
        "dataCadastro": "20/02/2022",
        "telefone": "44999802514"
    }
    ```
   * Observações:
     * Data deve ser enviada no formato "DD/MM/AAAA"
    
* Buscar usuário pelo ID [GET]: `/usuarios/1`
   * Resposta
   ```
    {
        "id": 1,
        "nome": "Emily",
        "email": "teste@gmail.com",
        "dataCadastro": "20/02/2022",
        "telefone": "44999802514"
    }
   ```
   
### Livro
* Criação de livro [POST]: `/livros`
  * Corpo da requisição
   ```
    {
        "titulo": "Senhor dos Aneis",
        "autor": "Tolkien",
        "isbn": "789456789",
        "dataPublicacao": "25/05/2000",
        "categoria": "Aventura"
    }
    ```
   * Resposta
    ```
    {
        "id": 1,
        "titulo": "Senhor dos Aneis",
        "autor": "Tolkien",
        "isbn": "789456789",
        "dataPublicacao": "25/05/2000",
        "categoria": "Aventura"
    }
    ```
   * Observações:
     * Data deve ser enviada no formato "DD/MM/AAAA"

* Buscar todos os livros [GET]: `/livros`
   * Resposta
   ```
   [
      {
          "id": 1,
          "titulo": "Senhor dos Aneis",
          "autor": "Tolkien",
          "isbn": "789456789",
          "dataPublicacao": "25/05/2000",
          "categoria": "Aventura"
      }
   ]
   ```
   
* Excluir livro [DELETE]: `/livros/1`
   * Resposta
     * Será apagado o livro com ID 1 e voltará o status 204 (No Content)
    
* Edição de livro [PUT]: `/livros/atualizar`
  * Corpo da requisição
   ```
    {
        "id": 1,
        "titulo": "Senhor dos Aneis",
        "autor": "Tolkien",
        "isbn": "789456789",
        "dataPublicacao": "25/05/2000",
        "categoria": "Aventura"
    }
    ```
   * Resposta
    ```
    {
        "id": 1,
        "titulo": "Senhor dos Aneis",
        "autor": "Tolkien",
        "isbn": "789456789",
        "dataPublicacao": "25/05/2000",
        "categoria": "Aventura"
    }
    ```
   * Observações:
     * Data deve ser enviada no formato "DD/MM/AAAA"
    
* Buscar livro pelo ID [GET]: `/livros/1`
   * Resposta
   ```
    {
        "id": 1,
        "titulo": "Senhor dos Aneis",
        "autor": "Tolkien",
        "isbn": "789456789",
        "dataPublicacao": "25/05/2000",
        "categoria": "Aventura"
    }
   ```

* Buscar recomendações de livro pelo ID do **usuário** [GET]: `/livros/recomendacoes/1`
   * Resposta
   ```
   [
      {
          "id": 1,
          "titulo": "Senhor dos Aneis",
          "autor": "Tolkien",
          "isbn": "789456789",
          "dataPublicacao": "25/05/2000",
          "categoria": "Aventura"
      }
   ]
   ```
   * Observações:
     * As recomendações serão baseadas nas categorias dos livros já emprestados pelo usuário
    
* Busca de livro no Google Books [GET]: `/livros/buscar-livros-google-books?nomeLivro=Senhor dos`
  * Resposta
  ```
  [
    {
        "id": null,
        "titulo": "O Senhor dos Anéis: A Sociedade do Anel",
        "autor": "J.R.R. Tolkien",
        "isbn": "9788595086333, 8595086338",
        "dataPublicacao": "25/11/2019",
        "categoria": "Fiction"
    },
    ...
  ]
  ```   
    
### Empréstimo
* Buscar todos os empréstimos [GET]: `/emprestimos`
   * Resposta
   ```
   [
        {
            "id": 1,
            "usuario": {
                "id": 1,
                "nome": "Emily",
                "email": "teste@gmail.com",
                "dataCadastro": "20/02/2022",
                "telefone": "44999802514"
            },
            "livro": {
                "id": 1,
                "titulo": "Senhor dos Aneis",
                "autor": "Tolkien",
                "isbn": "789456789",
                "dataPublicacao": "25/05/2000",
                "categoria": "Aventura"
            },
            "dataEmprestimo": "18/08/2024",
            "dataDevolucao": "20/08/2024",
            "status": "ABERTO"
        }
    ]
   ```
   
* Criação de empréstimo [POST]: `/emprestimos`
  * Corpo da requisição
   ```
    {
        "usuarioId": 1,
        "livroId": 1,
        "status": "ABERTO",
        "dataEmprestimo": "25/05/2024",
        "dataDevolucao": "25/09/2024"
    }
    ```
   * Resposta
    ```
    {
        "id": 1,
        "usuario": {
            "id": 1,
            "nome": "Emily",
            "email": "teste@gmail.com",
            "dataCadastro": "20/02/2022",
            "telefone": "44999802514"
        },
        "livro": {
            "id": 1,
            "titulo": "Senhor dos Aneis",
            "autor": "Tolkien",
            "isbn": "789456789",
            "dataPublicacao": "25/05/2000",
            "categoria": "Aventura"
        },
        "dataEmprestimo": "25/05/2024",
        "dataDevolucao": "25/09/2024",
        "status": "ABERTO"
    }
    ```
   * Observações:
     * Data deve ser enviada no formato "DD/MM/AAAA"
     * Status possíveis para um empréstimo: ABERTO ou FINALIZADO
   

* Editar empréstimo [PUT]: `/emprestimos`
   * Corpo da requisição
   ```
    {
        "id": 1,
        "usuario": {
            "id": 1,
            "nome": "Emily",
            "email": "teste@gmail.com",
            "dataCadastro": "20/02/2022",
            "telefone": "44999802514"
        },
        "livro": {
            "id": 1,
            "titulo": "Senhor dos Aneis",
            "autor": "Tolkien",
            "isbn": "789456789",
            "dataPublicacao": "25/05/2000",
            "categoria": "Aventura"
        },
        "dataEmprestimo": "18/08/2024",
        "dataDevolucao": "21/08/2024",
        "status": "FINALIZADO"
    }
   ```  
   * Resposta
   ```
    {
        "id": 1,
        "usuario": {
            "id": 1,
            "nome": "Emily",
            "email": "teste@gmail.com",
            "dataCadastro": "20/02/2022",
            "telefone": "44999802514"
        },
        "livro": {
            "id": 1,
            "titulo": "Senhor dos Aneis",
            "autor": "Tolkien",
            "isbn": "789456789",
            "dataPublicacao": "25/05/2000",
            "categoria": "Aventura"
        },
        "dataEmprestimo": "18/08/2024",
        "dataDevolucao": "21/08/2024",
        "status": "FINALIZADO"
    }
   ```
  * Observações:
     * Só será possível alterar a data da devolução e o status do empréstimo
     * Status possíveis para um empréstimo: ABERTO ou FINALIZADO
