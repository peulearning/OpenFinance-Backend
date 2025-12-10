# 💰 FinStack (Open Finance Manager)

Um gerenciador financeiro moderno, **open-source** e **auto-hospedável**.
Conecta-se automaticamente aos seus bancos via **Open Finance**, centraliza transações e oferece insights inteligentes — o **sucessor espiritual técnico do Guiabolso/Organizze**.

---

## 📋 Visão do Projeto

O **FinStack** resolve o problema da fragmentação financeira.
Em vez de planilhas manuais, ele utiliza a infraestrutura do **Open Finance Brasil** para sincronizar contas correntes e cartões de crédito em tempo real.

O diferencial técnico está na **Engine de Classificação**, que usa regras de correspondência (Regex/MCC) e aprendizado histórico para categorizar transações automaticamente:

> Exemplo: `"iFood"` → **Alimentação**

Com isso, relatórios e gráficos são gerados automaticamente sem intervenção manual.

---

## 🏛️ Arquitetura do Sistema

O sistema segue uma abordagem de **Modular Monolith** (preparado para microserviços), priorizando **performance** e **consistência de dados**.

![Diagrama de Containers](https://raw.githubusercontent.com/peulearning/OpenFinance-Backend/master/backend/container_diagram.png)

```plaintext
graph TD
    User((Usuário))

    subgraph Frontend ["Frontend Application"]
        NextJS[Next.js Client]
    end

    subgraph Backend ["Backend System (Spring Boot)"]
        API_GW[API Gateway / Controller]
        Auth[User & Auth Service]
        Sync[Bank Sync Engine]
        Rules[Categorization Rules]
    end

    subgraph Infra ["Infrastructure"]
        DB[(PostgreSQL)]
        Cache[(Redis)]
        Queue[[RabbitMQ / Kafka]]
    end

    subgraph External ["External Providers"]
        OpenFinance[Open Finance Gateway\n(Pluggy/Belvo)]
        Bank[Instituições Bancárias]
    end

    User -->|HTTPS| NextJS
    NextJS -->|REST/JSON| API_GW

    API_GW --> Auth
    API_GW --> Sync

    Sync -->|Async Event| Queue
    Queue -->|Process Transaction| Rules

    Sync -->|Fetch Data| OpenFinance
    OpenFinance -->|OAuth2| Bank

    Auth & Sync & Rules --> DB
    Sync -->|Cache Tokens| Cache
```


> **Nota:** A imagem deve estar acessível no caminho especificado para ser renderizada corretamente onde você deseja usá-la. Certifique-se de que o caminho seja correto conforme o ambiente em que você está trabalhando.

---

### 🔧 Componentes Chave

#### **Core API (Spring Boot)**
Gerencia autenticação (JWT), regras de negócio, persistência e sincronização.

#### **Sync Worker (Assíncrono)**
Processa sincronizações bancárias via RabbitMQ/Kafka para evitar timeouts e desacoplar operações demoradas.

#### **Finance Engine**
Limpa descrições e categoriza transações utilizando MCC, Regex e histórico.

#### **Frontend (Next.js)**
Renderização híbrida (SSR/CSR) para dashboards rápidos e SEO-friendly.

---

## 🚀 Tecnologias Utilizadas

### 🖥️ Backend

- **Linguagem:** Java 21 LTS
- **Framework:** Spring Boot 3.2
- **Segurança:** Spring Security + OAuth2 Resource Server
- **Persistência:** Spring Data JPA + Hibernate
- **Banco:** PostgreSQL 16 (particionamento planejado)
- **Cache:** Redis
- **Mensageria:** RabbitMQ (opcional no dev)
- **Migrations:** Flyway
- **Testes:** JUnit 5, Mockito, Testcontainers

---

### 🌐 Frontend

- Next.js 14 (App Router)
- TypeScript
- Tailwind CSS
- Shadcn/UI
- React Query + Zustand
- Recharts
- Zod + React Hook Form

---

## ✨ Funcionalidades

- ✅ **Login Social & JWT**
- ✅ **Conexão Bancária (Open Finance / Pluggy)**
- ✅ **Dashboard Unificado**
- ✅ **Faturas de Cartão**
- ✅ **Auto-Categorização Inteligente**
- ✅ **Orçamentos com Alertas**

---

## 🛠️ Instalação e Execução

### 🔧 Pré-requisitos

- Docker & Docker Compose
- Java 21 JDK
- Node.js 20+

---

### **1. Subir Infraestrutura (Postgres + Redis)**

Suba os containers essenciais sem precisar instalar o Postgres localmente:

```bash
cd infra
docker-compose up -d
```

---

### **2. Executar o Backend**

Configure as variáveis de ambiente. Crie um arquivo .env na raiz do backend baseado no .env.example.

```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
# Servidor em http://localhost:8080
```

---

### **3. Execuar o frontend**

Crie um arquivo .env.local na raiz do frontend.

```bash
cd frontend
npm install
npm run dev
# O app estará disponível em http://localhost:3000
```

---

## 🔒Segurança e Variáveis de Ambiente

IMPORTANTE: Nunca comite chaves reais.

Exemplo de .env para o Backend:

```bash
# Banco de Dados
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/finstack_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres

# Open Finance Provider (Ex: Pluggy/Belvo)
OPEN_FINANCE_CLIENT_ID=seu_client_id
OPEN_FINANCE_CLIENT_SECRET=seu_client_secret

# Segurança (JWT)
JWT_SECRET=uma_chave_muito_longa_e_segura_base64
JWT_EXPIRATION=86400000
```

---

## 🛣️ Roadmap

 Arquitetura Base (Spring + Next.js)
 Integração OAuth com Google
 Conector Open Finance (Mock)
 Engine de Categorização v1 (Regex)
 Integração real com Pluggy.ai
 App Mobile (React Native - Futuro

---

## 🤝 Contribuição

Contribuições são bem-vindas! Por favor, leia o CONTRIBUTING.md para detalhes sobre nosso código de conduta e o processo para enviar pull requests.

---

## 📄 Licença

Este projeto está sob a licença MIT - veja o arquivo LICENSE.md para detalhes.

Desenvolvido com ☕ e Java por [Pedro Henrique/peulearning]
