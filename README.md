# task-manager-api

API REST simples para gerenciamento de tarefas, construída com Spring Boot. Feita como projeto de estudo de backend em Java.

Permite criar, listar, atualizar, concluir e remover tarefas, com validação de dados e tratamento de erros.

## Tech stack

- Java 17
- Spring Boot 4 (Web, Data JPA, Validation)
- H2 Database (em memória)
- Hibernate
- Lombok
- Springdoc OpenAPI / Swagger UI
- Maven

## Arquitetura

O código segue separação em camadas:

```
controller/    endpoints REST
service/       regras de negócio
repository/    acesso a dados (Spring Data JPA)
dto/           objetos de entrada e saída da API
model/         entidades JPA
exception/     exceções customizadas e handler global de erros
```

Fluxo de uma requisição: `Controller` recebe → chama `Service` → `Service` usa `Repository` para acessar o banco.

## Rodando localmente

Pré-requisito: JDK 17.

```
git clone https://github.com/KevinnSouza/task-manager-api.git
cd task-manager-api/taskmanager
mvnw.cmd spring-boot:run
```

No Linux/Mac, use `./mvnw spring-boot:run`.

A aplicação sobe em `http://localhost:8080`.

Banco H2 (console web): `http://localhost:8080/h2-console`
JDBC URL: `jdbc:h2:mem:taskdb` — usuário `sa`, sem senha.

Documentação Swagger: `http://localhost:8080/swagger-ui/index.html`

## Endpoints

```
GET    /api/tasks                    lista todas as tarefas
GET    /api/tasks?completed=true     filtra por concluídas/pendentes
GET    /api/tasks/{id}               busca uma tarefa por id
POST   /api/tasks                    cria uma tarefa
PUT    /api/tasks/{id}               atualiza uma tarefa
PATCH  /api/tasks/{id}/completar     marca uma tarefa como concluída
DELETE /api/tasks/{id}               remove uma tarefa
```

### Criar tarefa

```
POST /api/tasks
Content-Type: application/json

{
  "title": "Estudar Spring Boot",
  "description": "Terminar o projeto",
  "priority": "HIGH"
}
```

Resposta (201):

```
{
  "id": 1,
  "title": "Estudar Spring Boot",
  "description": "Terminar o projeto",
  "priority": "HIGH",
  "completed": false,
  "createdAt": "2026-07-27T23:18:36.77965",
  "completedAt": null
}
```

## Regras de negócio

- Tarefa sem prioridade informada nasce com prioridade `MEDIUM`.
- Tentar concluir uma tarefa já concluída retorna `409`.
- `title` é obrigatório, até 100 caracteres. `description` é opcional, até 500.
- Buscar/atualizar/concluir/remover um id inexistente retorna `404`.

## Documentação interativa

![swagger-ui](C:\Users\souza\OneDrive\Área de Trabalho\java\swagger-ui.png)

## Testado manualmente via Postman

- criação, listagem, busca por id, atualização, remoção
- validação de título obrigatório (400)
- conclusão duplicada (409)
- busca de id inexistente (404)
- filtro por status

## Possíveis próximos passos

- trocar H2 por PostgreSQL + Docker
- testes automatizados
- paginação
- autenticação