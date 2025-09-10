# API de Gerenciamento de Super-Heróis

## Descrição

Esta é uma API REST desenvolvida em Java com Spring Boot para gerenciamento de super-heróis. A aplicação permite realizar operações CRUD (Create, Read, Update, Delete) em uma base de dados de super-heróis e seus superpoderes.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **Hibernate**
- **H2 Database** (banco em memória)
- **Maven**
- **JUnit 5** e **Mockito** (para testes)
- **Springdoc OpenAPI** / **Swagger UI**

## Funcionalidades

- ✅ Cadastro de um novo super-herói
- ✅ Listagem de todos os super-heróis
- ✅ Consulta de um super-herói por ID
- ✅ Atualização de informações do super-herói por ID
- ✅ Exclusão de um super-herói por ID
- ✅ Listagem de superpoderes disponíveis
- ✅ Validação de dados de entrada
- ✅ Tratamento de exceções customizado
- ✅ Testes unitários

## Estrutura do Banco de Dados

### Tabela: herois
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | int (PK) | Identificador único do herói |
| nome | nvarchar(120) | Nome real do herói |
| nome_heroi | nvarchar(120) | Nome do super-herói (único) |
| data_nascimento | datetime2(7) | Data de nascimento |
| altura | float | Altura em metros |
| peso | float | Peso em quilogramas |

### Tabela: superpoderes
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | int (PK) | Identificador único do superpoder |
| superpoder | nvarchar(50) | Nome do superpoder |
| descricao | nvarchar(250) | Descrição do superpoder |

### Tabela: herois_superpoderes
| Campo | Tipo | Descrição |
|-------|------|-----------|
| heroi_id | int (FK) | ID do herói |
| superpoder_id | int (FK) | ID do superpoder |

## Endpoints da API

### Heróis

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/herois` | Criar um novo herói |
| GET | `/api/herois` | Listar todos os heróis |
| GET | `/api/herois/{id}` | Buscar herói por ID |
| PUT | `/api/herois/{id}` | Atualizar herói por ID |
| DELETE | `/api/herois/{id}` | Deletar herói por ID |
| GET | `/api/herois/superpoderes` | Listar todos os superpoderes |

### Exemplos de Requisições

#### Criar Herói (POST /api/herois)
```json
{
  "nome": "Clark Kent",
  "nomeHeroi": "Superman",
  "dataNascimento": "1938-06-01 00:00:00",
  "altura": 1.91,
  "peso": 102.0,
  "superpoderesIds": [1, 2, 3]
}
```

#### Resposta de Sucesso
```json
{
  "id": 1,
  "nome": "Clark Kent",
  "nomeHeroi": "Superman",
  "dataNascimento": "1938-06-01 00:00:00",
  "altura": 1.91,
  "peso": 102.0,
  "superpoderes": [
    {
      "id": 1,
      "superpoder": "Super Força",
      "descricao": "Capacidade de exercer força física muito além do normal humano"
    }
  ]
}
```

## Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior

### Passos para execução

1. **Clone o repositório:**
```bash
git clone <url-do-repositorio>
cd superheroes-api
```

2. **Compile e execute o projeto:**
```bash
mvn clean install
mvn spring-boot:run
```

3. **Acesse a aplicação:**
- API: http://localhost:8080
- Console H2: http://localhost:8080/h2-console
    - URL: jdbc:h2:mem:testdb
    - Usuario: sa
    - Senha: password

Acesse a interface Swagger para explorar e testar os endpoints da API:  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Executar Testes
```bash
mvn test
```

## Validações Implementadas

- **Nome**: obrigatório, não pode ser vazio
- **Nome do Herói**: obrigatório, não pode ser vazio, deve ser único
- **Data de Nascimento**: obrigatória
- **Altura**: obrigatória, deve ser um valor positivo
- **Peso**: obrigatório, deve ser um valor positivo
- **Superpoderes**: pelo menos um superpoder deve ser selecionado

## Tratamento de Erros

A API retorna códigos HTTP apropriados e mensagens de erro estruturadas:

- **400 Bad Request**: Dados de entrada inválidos
- **404 Not Found**: Recurso não encontrado
- **409 Conflict**: Nome de herói já existe
- **500 Internal Server Error**: Erro interno do servidor

### Exemplo de Resposta de Erro
```json
{
  "status": 404,
  "error": "Herói não encontrado",
  "message": "Herói com ID 999 não encontrado",
  "timestamp": "2025-01-01T12:00:00"
}
```

## Dados Iniciais

A aplicação é inicializada com:
- 15 superpoderes pré-cadastrados
- 2 heróis de exemplo (Superman e Spider-Man)

## Decisões de Arquitetura

### Por que essa estrutura?

1. **Separação em Camadas**: Utilizei o padrão MVC com separação clara entre Controller, Service, Repository e Entity para manter o código organizado e facilitar manutenção.

2. **DTOs**: Implementei DTOs separados para Request e Response para ter controle sobre os dados que entram e saem da API, além de evitar exposição desnecessária das entidades.

3. **Tratamento Global de Exceções**: Criei um GlobalExceptionHandler para centralizar o tratamento de erros e padronizar as respostas.

4. **Relacionamento Many-to-Many**: Modelei a relação entre Heróis e Superpoderes usando uma tabela intermediária explícita (HeroiSuperpoder) para ter maior controle sobre o relacionamento.

5. **Validações**: Utilizei Bean Validation para garantir a integridade dos dados tanto no nível de entidade quanto de DTO.

6. **Testes**: Implementei testes unitários para as camadas de Service e Controller para garantir a qualidade do código.


## Objetivo

Desenvolvido como parte do desafio técnico para Desenvolvedor Full Stack.