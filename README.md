# Trabalho2-tbd
🚀 Gerenciador de Eventos - Persistência Poliglota

Este é um projeto desenvolvido em Java (Spring Boot) que implementa o conceito de Persistência Poliglota. O sistema utiliza três bancos de dados diferentes de forma simultânea e integrada para extrair o melhor de cada paradigma de armazenamento.

🛠️ Tecnologias e Bancos de Dados Utilizados

Java 17+ com Spring Boot 3.x

MongoDB (NoSQL Orientado a Documentos): Banco de dados principal da aplicação. Ideal para armazenar detalhes flexíveis dos Eventos e Pessoas (ex: detalhesExtras em formato JSON dinâmico).

Neo4j (NoSQL Orientado a Grafos): Responsável por gerenciar as complexas redes de relacionamento (Quem organizou e quem participou de cada evento).

PostgreSQL (Relacional SQL): Utilizado para receber a exportação padronizada dos dados dos eventos para análises estruturadas.

Docker & Docker Compose: Para orquestrar e subir os três bancos de dados instantaneamente.

⚙️ Como rodar o projeto

Pré-requisitos

Docker e Docker Compose instalados.

Java JDK 17 ou superior.

Maven instalado (ou usar o mvnw embutido no projeto).

Passo a passo

Clone o repositório:

git clone [https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git](https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git)
cd SEU_REPOSITORIO


Suba os Bancos de Dados (Docker):
Na raiz do projeto (onde está o arquivo docker-compose.yml), execute o comando:

docker-compose up -d


(Isso iniciará o MongoDB na porta 27017, o Neo4j na porta 7687/7474 e o PostgreSQL na porta 5432).

Inicie a Aplicação Spring Boot:
Você pode rodar pelo VS Code/IntelliJ ou pelo terminal:

./mvnw clean spring-boot:run


A aplicação estará rodando em: http://localhost:8080

📡 Documentação da API (Rotas)

Abaixo estão todas as rotas disponíveis no sistema para teste via Postman, Insomnia, Thunder Client ou Navegador.

1. ⚙️ Rotas Administrativas & Dashboard

Método

Endpoint

Descrição

GET

/api/admin/popular-banco

[ATALHO] Cria eventos, pessoas e relações automaticamente para facilitar testes iniciais.

GET

/api/admin/dashboard

Retorna um resumo integrado do total de registros no MongoDB, nós no Neo4j e registros no PostgreSQL.

POST

/api/admin/exportar

Exporta todos os eventos estruturados do MongoDB para a tabela relacional do PostgreSQL.

2. 📅 Gestão de Eventos (MongoDB + Neo4j)

Criar Evento

Método: POST

URL: /api/eventos

Body (JSON):

{
  "titulo": "Tech Summit 2026",
  "local": "São Paulo",
  "data": "2026-10-15",
  "detalhesExtras": {
    "patrocinador": "Google",
    "capacidade": 500
  }
}


Listar e Filtrar Eventos

Método: GET

URL: /api/eventos

Filtros Opcionais: /api/eventos?local=São Paulo ou ?data=2026-10-15 ou ?palavraChave=Tech

Atualizar Evento

Método: PUT

URL: /api/eventos/{ID_DO_EVENTO}

Body: (Mesmo formato JSON da criação)

Deletar Evento

Método: DELETE

URL: /api/eventos/{ID_DO_EVENTO}

3. 👥 Gestão de Pessoas (MongoDB + Neo4j)

Criar Pessoa

Método: POST

URL: /api/pessoas

Body (JSON):

{
  "nome": "João Silva",
  "email": "joao@email.com"
}


4. 🕸️ Relacionamentos em Grafo (Neo4j Exclusivo)

Registrar Participação em um Evento

Método: POST

URL: /api/pessoas/{ID_DA_PESSOA}/participa/{ID_DO_EVENTO}?organizador=false

Nota: Use ?organizador=true se a pessoa for da organização.

Promover Participante a Organizador

Método: PUT

URL: /api/pessoas/{ID_DA_PESSOA}/promove/{ID_DO_EVENTO}

Nota: O sistema mantém o histórico duplo (A pessoa fica listada como Participante E Organizadora).

Listar Participantes de um Evento

Método: GET

URL: /api/pessoas/evento/{ID_DO_EVENTO}/participantes

Listar Organizadores de um Evento

Método: GET

URL: /api/pessoas/evento/{ID_DO_EVENTO}/organizadores

🎯 Soluções Técnicas Implementadas

Durante o desenvolvimento, foi aplicado o contorno para o bug clássico de Polyglot Persistence do Spring Data Neo4j em conjunto com o JPA/Postgres. O conflito no mapeamento de entidades foi resolvido injetando diretamente o Driver Nativo do Neo4j (org.neo4j.driver.Driver) nos Services, permitindo a execução segura de Queries Cypher transacionais para as operações de Grafos sem interferência do contexto relacional.
