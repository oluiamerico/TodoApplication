# 🧾 TodoApplication — API REST com Spring Boot, JWT e Segurança por Usuário

Uma aplicação Spring Boot para gerenciamento de tarefas com autenticação baseada em token JWT e controle de acesso por usuário autenticado.

## 🌟 Sobre o Projeto

Este é um projeto de exemplo de uma API RESTful feita com Spring Boot, onde os usuários podem:

- 🔐 Registrar-se e fazer login
- 🔑 Receber um token JWT após autenticação
- 📋 Criar, ler, atualizar e deletar tarefas (CRUD)
- 🔒 Acessar apenas suas próprias tarefas

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão |
|------------|--------|
| Java | 17+ |
| Spring Boot | 3.4.5 |
| Spring Security | 6.4.5 |
| JWT (jjwt) | 0.11.5 |
| PostgreSQL | 42.7.5 |
| Lombok | 1.18.38 |
| IDE | IntelliJ IDEA |

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com.luiamerico.todoapplication/
│   │       ├── auth/         # Configuração JWT e filtro de segurança
│   │       ├── config/       # Configuração do Spring Security
│   │       ├── controller/   # Controladores REST
│   │       ├── dto/          # Data Transfer Objects (DTOs)
│   │       ├── model/        # Entidades JPA (User, Task)
│   │       ├── repository/   # Interfaces de acesso ao banco
│   │       ├── service/      # Lógica de negócio
│   │       └── utils/        # Utilitários (ex: SecurityUtil)
```

## 🔐 Endpoints da API

### 🔑 Autenticação

#### 📌 Registrar novo usuário
```
POST /api/auth/register
```

#### 📌 Fazer login e obter token JWT
```
POST /api/auth/login
```

Exemplo de JSON para login:
```json
{
  "email": "usuario1@test.com",
  "password": "123456"
}
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.xxxxx..."
}
```

### 📝 Tarefas (Requer token JWT)

Enviar token no header:
```
Authorization: Bearer <token>
```

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | /api/tasks | Lista todas as tarefas do usuário logado |
| GET | /api/tasks/{id} | Retorna uma tarefa específica |
| POST | /api/tasks | Cria nova tarefa |
| PUT | /api/tasks/{id} | Atualiza uma tarefa do usuário |
| DELETE | /api/tasks/{id} | Deleta uma tarefa do usuário |

Exemplo de JSON para criar tarefa:
```json
{
  "title": "Estudar Spring Boot",
  "description": "Criar sistema com segurança JWT",
  "completed": false
}
```

## 🔐 Proteção por Usuário

- Cada tarefa está associada a um usuário.
- ➡️ Um usuário só pode acessar suas próprias tarefas.
- Qualquer tentativa de acessar tarefas de outro usuário retorna 403 Forbidden.

## 🗃️ Estrutura do Banco de Dados

### Tabela users

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGINT | Chave primária |
| username | VARCHAR | Nome do usuário |
| email | VARCHAR | Email único |
| password | VARCHAR | Senha criptografada |

### Tabela tasks

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGINT | Chave primária |
| title | VARCHAR | Título da tarefa |
| description | VARCHAR | Descrição (opcional) |
| completed | BOOLEAN | Estado da tarefa |
| created_at | VARCHAR | Data de criação |
| due_date | VARCHAR | Data de vencimento |
| user_id | BIGINT | FK para a tabela users |

## 🧪 Testes com Postman (ou qualquer cliente HTTP)

#### ✅ Registrar Usuário
```
POST http://localhost:8080/api/auth/register
```

#### ✅ Fazer Login
```
POST http://localhost:8080/api/auth/login
```

#### ✅ Criar Tarefa
```
POST http://localhost:8080/api/tasks
```

Headers:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.xxxxx...
```

#### ✅ Listar Tarefas
```
GET http://localhost:8080/api/tasks
```
