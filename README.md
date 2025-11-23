# 🐾 Petshop API

API RESTful desenvolvida em **Java** com **Spring Boot** para o gerenciamento completo de um Petshop. O sistema controla clientes, pets, serviços, tipos de serviço e realiza agendamentos com validação inteligente de horários.

## 🚀 Tecnologias Utilizadas

* ![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
* ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
* ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
* **Lombok** (Produtividade e redução de código boilerplate)
* **Maven** (Gerenciamento de dependências)

---

## ⚙️ Funcionalidades e Arquitetura

O projeto segue uma arquitetura em camadas (Controller, Service, Repository, Model) e utiliza o padrão **DTO (Data Transfer Object)** para garantir segurança e desacoplamento entre a API e o banco de dados.

### Principais Recursos:
* **CRUD Completo:** Gerenciamento de Usuários, Pets, Serviços, Tipos de Serviço e Agendamentos.
* **Relacionamentos JPA:**
    * `@OneToMany`: Um Usuário possui vários Pets.
    * `@ManyToOne`: Um Serviço pertence a um Tipo; Agendamento pertence a Pet e Serviço.
* **Regras de Negócio:**
    * **Validação de Conflito de Agenda:** O sistema impede o cadastro de dois agendamentos no mesmo horário (exceto se o anterior estiver cancelado).
    * **Proteção de Dados:** DTOs de atualização impedem a alteração de dados sensíveis (ex: dono do pet, IDs).
    * **Status de Agendamento:** Fluxo controlado via Enum (`PENDENTE`, `CONFIRMADO`, `CONCLUIDO`, `CANCELADO`).

---

## 🔌 Endpoints da API

### 📅 Agendamentos (`/agendamento`)

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/agendamento` | Lista todos os agendamentos (com detalhes do Pet, Dono e Serviço). |
| `POST` | `/agendamento` | Cria um novo agendamento (valida disponibilidade de horário). |
| `PUT` | `/agendamento/{id}` | Atualiza dados ou status do agendamento. |
| `DELETE` | `/agendamento/{id}` | Remove um agendamento. |

**Exemplo de JSON (Criar Agendamento):**
```json
{
  "dataHora": "2023-12-25T14:30:00",
  "observacoes": "Cão sensível a barulho, cuidado na secagem.",
  "petId": 1,
  "servicoId": 1
}
```

**Exemplo de JSON (Atualizar Status):**
```json
{
  "status": "CONFIRMADO",
  "observacoes": "Cliente confirmou via WhatsApp"
}
```

### 🚿 Serviços (`/servico`) e Tipos (`/tipo-servico`)

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/servico` | Lista serviços disponíveis. |
| `POST` | `/servico` | Cadastra novo serviço. |
| `POST` | `/tipo-servico` | Cadastra categoria (ex: Estética, Veterinário). |

**Exemplo de JSON (Criar Serviço):**
```json
{
  "nome": "Banho Premium - Porte Pequeno",
  "descricao": "Banho com hidratação profunda",
  "preco": 80.00,
  "duracaoMinutos": 60,
  "ativo": true,
  "tipoServicoId": 1
}
```

### 🐶 Pets (/pet) e Usuários (/usuario)
| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/usuario` | Cadastra um novo cliente. |
| `POST` | `/pet` | Cadastra um pet vinculado a um cliente. |

**Exemplo de JSON (Criar Pet):**
```json
{
  "nome": "Arcoiris",
  "idade": 1,
  "raca": "Calopsita",
  "tipo": "PASSARO",
  "peso": 0.09,
  "usuario": {
      "id": 1
  }
}
```
### 📦 Como Executar o Projeto

1. Clone o repositório:
```bash
git clone [https://github.com/seu-usuario/petshop-api.git](https://github.com/seu-usuario/petshop-api.git)
```
2. Entre na pasta do projeto:
```bash
cd petshop-api
```
3. Execute com Maven (Terminal):
```bash
./mvnw spring-boot:run
```
4. Acesse a API:
- URL Base: *http://localhost:8080*

### 👨‍💻 Autor
API (Backend) desenvolvida por Ricardo Lopes Tomaz.
Projeto criado para fins de estudo e implementação de boas práticas em desenvolvimento Java.




