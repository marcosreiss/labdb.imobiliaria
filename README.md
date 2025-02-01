# Sistema de Gestão Imobiliária

Este projeto consiste em um **backend** para a gestão de operações diárias de uma imobiliária. O sistema permite o gerenciamento de **imóveis, clientes, locações, profissionais, serviços e pagamentos de aluguéis**.

## 📌 Funcionalidades Principais

- **Cadastro de Imóveis**: Inserção, atualização e listagem de imóveis disponíveis.
- **Cadastro de Profissionais**: Gerenciamento de profissionais da imobiliária.
- **Gestão de Locações**: Associação de imóveis a clientes (inquilinos) e verificação de disponibilidade antes da locação.
- **Serviços Prestados**: Registro de serviços executados para locações específicas.
- **Gerenciamento de Aluguéis**: Controle de valores, vencimentos e atrasos nos pagamentos.
- **Registro de Pagamentos**: Cálculo de multas para pagamentos atrasados.

## 🛠️ Tecnologias Utilizadas

Este projeto utiliza as seguintes dependências para gerenciamento de dados e simplificação do código:

- **[Jakarta Persistence API (JPA)](https://jakarta.ee/)** – Para mapeamento objeto-relacional (ORM).
- **[Hibernate](https://hibernate.org/)** – Implementação do JPA utilizada para interagir com o banco de dados.
- **[MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)** – Driver para conexão com o banco de dados MySQL.
- **[Lombok](https://projectlombok.org/)** – Redução de código repetitivo, como getters, setters e construtores.

## 🏗️ Arquitetura do Projeto

O sistema foi desenvolvido seguindo uma **arquitetura em camadas**, garantindo **coesão e baixo acoplamento**:

1. **Camada de Modelo**: Contém as entidades mapeadas com JPA.
2. **Camada de Acesso a Dados (Repository)**: Implementa operações CRUD para manipulação dos dados.
3. **Camada de Serviço**: Contém as regras de negócio, como verificação de disponibilidade de imóveis.
4. **Testes**: Cada funcionalidade implementada possui um método `main` para teste.

## 👨‍💻 Desenvolvido por

- **Marcos Vinicius Santos**
- **Maria Luiza**

## 🚀 Como Executar o Projeto

1. **Configurar o banco de dados MySQL**:
   - Criar um banco chamado `imobiliaria_db`
   - Ajustar credenciais no arquivo `persistence.xml`

2. **Compilar e executar**:
   - Importar o projeto em uma IDE como IntelliJ ou Eclipse.
   - Certificar-se de que o **Lombok está habilitado**.
   - Rodar os métodos `main` para testar as funcionalidades.

---

Este projeto foi desenvolvido como parte do **Laboratório de Banco de Dados - DCOMP/IFMA**.
