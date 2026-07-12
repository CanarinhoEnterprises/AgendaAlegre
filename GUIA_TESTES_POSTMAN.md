# 📋 GUIA COMPLETO DE TESTES - AgendaAlegre API

## 🚀 Configuração Base

**URL Base:** `http://localhost:8080`  
**Porta Default:** 8080  
**Formato de Requisição:** JSON

---

## 📦 MÓDULO 1: USUÁRIOS

### 1.1 - Cadastrar Usuário

**Endpoint:** `POST /usuarios`

```bash
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@exemplo.com",
    "cpf": "12345678900",
    "cnpj": null,
    "telefone": "85987654321",
    "ativo": true,
    "senha": "senha123"
  }'
```

**Resposta (201 Created):**
```json
{
  "idUsuario": 1,
  "nome": "João Silva",
  "email": "joao@exemplo.com",
  "cpf": "12345678900",
  "cnpj": null,
  "telefone": "85987654321",
  "ativo": true
}
```

**Nota:** A `senha` NÃO é retornada na resposta por segurança.

---

### 1.2 - Listar Todos os Usuários

**Endpoint:** `GET /usuarios`

```bash
curl -X GET http://localhost:8080/usuarios
```

**Resposta (200 OK):**
```json
[
  {
    "idUsuario": 1,
    "nome": "João Silva",
    "email": "joao@exemplo.com",
    "cpf": "12345678900",
    "cnpj": null,
    "telefone": "85987654321",
    "ativo": true
  }
]
```

---

### 1.3 - Consultar Usuário por ID

**Endpoint:** `GET /usuarios/{id}`

```bash
curl -X GET http://localhost:8080/usuarios/1
```

**Resposta (200 OK):**
```json
{
  "idUsuario": 1,
  "nome": "João Silva",
  "email": "joao@exemplo.com",
  "cpf": "12345678900",
  "cnpj": null,
  "telefone": "85987654321",
  "ativo": true
}
```

---

### 1.4 - Atualizar Usuário

**Endpoint:** `PUT /usuarios/{id}`

```bash
curl -X PUT http://localhost:8080/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João da Silva Santos",
    "email": "joao.silva@exemplo.com",
    "cpf": "12345678900",
    "cnpj": null,
    "telefone": "85988888888",
    "ativo": true,
    "senha": "novaSenha456"
  }'
```

**Resposta (200 OK):**
```json
{
  "idUsuario": 1,
  "nome": "João da Silva Santos",
  "email": "joao.silva@exemplo.com",
  "cpf": "12345678900",
  "cnpj": null,
  "telefone": "85988888888",
  "ativo": true
}
```

---

### 1.5 - Deletar Usuário

**Endpoint:** `DELETE /usuarios/{id}`

```bash
curl -X DELETE http://localhost:8080/usuarios/1
```

**Resposta (204 No Content)**

---

## 📍 MÓDULO 2: CIDADES

### 2.1 - Cadastrar Cidade

**Endpoint:** `POST /cidades`

```bash
curl -X POST http://localhost:8080/cidades \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Fortaleza",
    "estado": "CE"
  }'
```

**Resposta (201 Created):**
```json
{
  "idCidade": 1,
  "nome": "Fortaleza",
  "estado": "CE"
}
```

---

### 2.2 - Listar Cidades

**Endpoint:** `GET /cidades`

```bash
curl -X GET http://localhost:8080/cidades
```

---

### 2.3 - Consultar Cidade por ID

**Endpoint:** `GET /cidades/{id}`

```bash
curl -X GET http://localhost:8080/cidades/1
```

---

### 2.4 - Atualizar Cidade

**Endpoint:** `PUT /cidades/{id}`

```bash
curl -X PUT http://localhost:8080/cidades/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Fortaleza - Capital",
    "estado": "CE"
  }'
```

---

### 2.5 - Deletar Cidade

**Endpoint:** `DELETE /cidades/{id}`

```bash
curl -X DELETE http://localhost:8080/cidades/1
```

---

## 🏠 MÓDULO 3: ENDEREÇOS

### 3.1 - Cadastrar Endereço

**Endpoint:** `POST /enderecos`

```bash
curl -X POST http://localhost:8080/enderecos \
  -H "Content-Type: application/json" \
  -d '{
    "rua": "Rua Principal",
    "numero": "500",
    "bairro": "Centro",
    "complemento": "Apto 205",
    "idCidade": 1
  }'
```

**Resposta (201 Created):**
```json
{
  "idEndereco": 1,
  "rua": "Rua Principal",
  "numero": "500",
  "bairro": "Centro",
  "complemento": "Apto 205",
  "idCidade": 1
}
```

---

### 3.2 - Listar Endereços

**Endpoint:** `GET /enderecos`

```bash
curl -X GET http://localhost:8080/enderecos
```

---

### 3.3 - Consultar Endereço por ID

**Endpoint:** `GET /enderecos/{id}`

```bash
curl -X GET http://localhost:8080/enderecos/1
```

---

### 3.4 - Atualizar Endereço

**Endpoint:** `PUT /enderecos/{id}`

```bash
curl -X PUT http://localhost:8080/enderecos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "rua": "Avenida Paulista",
    "numero": "1000",
    "bairro": "Bela Vista",
    "complemento": "Sala 301",
    "idCidade": 1
  }'
```

---

### 3.5 - Deletar Endereço

**Endpoint:** `DELETE /enderecos/{id}`

```bash
curl -X DELETE http://localhost:8080/enderecos/1
```

---

## 🏷️ MÓDULO 4: TIPOS DE ESPAÇO

### 4.1 - Cadastrar Tipo de Espaço

**Endpoint:** `POST /tipos-espaco`

```bash
curl -X POST http://localhost:8080/tipos-espaco \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Auditório",
    "descricao": "Espaço para públicos eventos e apresentações"
  }'
```

**Resposta (201 Created):**
```json
{
  "idTipoEspaco": 1,
  "nome": "Auditório",
  "descricao": "Espaço para públicos eventos e apresentações"
}
```

---

### 4.2 - Listar Tipos de Espaço

**Endpoint:** `GET /tipos-espaco`

```bash
curl -X GET http://localhost:8080/tipos-espaco
```

---

### 4.3 - Consultar Tipo de Espaço por ID

**Endpoint:** `GET /tipos-espaco/{id}`

```bash
curl -X GET http://localhost:8080/tipos-espaco/1
```

---

### 4.4 - Atualizar Tipo de Espaço

**Endpoint:** `PUT /tipos-espaco/{id}`

```bash
curl -X PUT http://localhost:8080/tipos-espaco/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Grande Auditório",
    "descricao": "Auditório com capacidade para grandes eventos"
  }'
```

---

### 4.5 - Deletar Tipo de Espaço

**Endpoint:** `DELETE /tipos-espaco/{id}`

```bash
curl -X DELETE http://localhost:8080/tipos-espaco/1
```

---

## 📄 MÓDULO 5: TIPOS DE DOCUMENTO

### 5.1 - Cadastrar Tipo de Documento

**Endpoint:** `POST /tipos-doc`

```bash
curl -X POST http://localhost:8080/tipos-doc \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "RG",
    "descricao": "Registro Geral de Identidade"
  }'
```

**Resposta (201 Created):**
```json
{
  "idTipoDoc": 1,
  "nome": "RG",
  "descricao": "Registro Geral de Identidade"
}
```

---

### 5.2 - Listar Tipos de Documento

**Endpoint:** `GET /tipos-doc`

```bash
curl -X GET http://localhost:8080/tipos-doc
```

---

### 5.3 - Consultar Tipo de Documento por ID

**Endpoint:** `GET /tipos-doc/{id}`

```bash
curl -X GET http://localhost:8080/tipos-doc/1
```

---

### 5.4 - Atualizar Tipo de Documento

**Endpoint:** `PUT /tipos-doc/{id}`

```bash
curl -X PUT http://localhost:8080/tipos-doc/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "RG (Renovado)",
    "descricao": "Registro Geral de Identidade (Com foto digital)"
  }'
```

---

### 5.5 - Deletar Tipo de Documento

**Endpoint:** `DELETE /tipos-doc/{id}`

```bash
curl -X DELETE http://localhost:8080/tipos-doc/1
```

---

## 📋 MÓDULO 6: MODELOS DE TERMO

### 6.1 - Cadastrar Modelo de Termo

**Endpoint:** `POST /modelos-termo`

```bash
curl -X POST http://localhost:8080/modelos-termo \
  -H "Content-Type: application/json" \
  -d '{
    "versao": 1,
    "titulo": "Termo de Responsabilidade",
    "texto": "O solicitante se responsabiliza pela conservação do espaço...",
    "descricao": "Termo padrão para uso de espaços públicos",
    "ativo": true
  }'
```

**Resposta (201 Created):**
```json
{
  "idModelo": 1,
  "versao": 1,
  "titulo": "Termo de Responsabilidade",
  "texto": "O solicitante se responsabiliza pela conservação do espaço...",
  "descricao": "Termo padrão para uso de espaços públicos",
  "ativo": true,
  "dtCriacao": "2024-01-15"
}
```

---

### 6.2 - Listar Modelos de Termo

**Endpoint:** `GET /modelos-termo`

```bash
curl -X GET http://localhost:8080/modelos-termo
```

---

### 6.3 - Consultar Modelo de Termo por ID

**Endpoint:** `GET /modelos-termo/{id}`

```bash
curl -X GET http://localhost:8080/modelos-termo/1
```

---

### 6.4 - Atualizar Modelo de Termo

**Endpoint:** `PUT /modelos-termo/{id}`

```bash
curl -X PUT http://localhost:8080/modelos-termo/1 \
  -H "Content-Type: application/json" \
  -d '{
    "versao": 2,
    "titulo": "Termo de Responsabilidade v2",
    "texto": "Versão atualizada do termo...",
    "descricao": "Termo atualizado 2024",
    "ativo": true
  }'
```

---

### 6.5 - Deletar Modelo de Termo

**Endpoint:** `DELETE /modelos-termo/{id}`

```bash
curl -X DELETE http://localhost:8080/modelos-termo/1
```

---

## 🎖️ MÓDULO 7: RECURSOS DE ACESSIBILIDADE

### 7.1 - Cadastrar Recurso de Acessibilidade

**Endpoint:** `POST /recursos-acessibilidade`

```bash
curl -X POST http://localhost:8080/recursos-acessibilidade \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Rampa de Acesso",
    "descricao": "Rampa com inclinação acessível para pessoas com mobilidade reduzida"
  }'
```

**Resposta (201 Created):**
```json
{
  "idRecurso": 1,
  "nome": "Rampa de Acesso",
  "descricao": "Rampa com inclinação acessível para pessoas com mobilidade reduzida"
}
```

---

### 7.2 - Listar Recursos

**Endpoint:** `GET /recursos-acessibilidade`

```bash
curl -X GET http://localhost:8080/recursos-acessibilidade
```

---

### 7.3 - Consultar Recurso por ID

**Endpoint:** `GET /recursos-acessibilidade/{id}`

```bash
curl -X GET http://localhost:8080/recursos-acessibilidade/1
```

---

### 7.4 - Atualizar Recurso

**Endpoint:** `PUT /recursos-acessibilidade/{id}`

```bash
curl -X PUT http://localhost:8080/recursos-acessibilidade/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Rampa de Acesso Melhorada",
    "descricao": "Rampa com manutenção realizada"
  }'
```

---

### 7.5 - Deletar Recurso

**Endpoint:** `DELETE /recursos-acessibilidade/{id}`

```bash
curl -X DELETE http://localhost:8080/recursos-acessibilidade/1
```

---

## 🏢 MÓDULO 8: ESPAÇOS

### 8.1 - Cadastrar Espaço (COM RECURSOS DE ACESSIBILIDADE)

**Endpoint:** `POST /espacos`

```bash
curl -X POST http://localhost:8080/espacos \
  -H "Content-Type: application/json" \
  -d '{
    "idEndereco": 1,
    "idTipoEspaco": 1,
    "capacidade": 150,
    "nome": "Auditório Principal",
    "descricao": "Auditório moderno com equipamentos de apresentação",
    "urlCapa": "https://exemplo.com/img/auditorio.jpg",
    "status": "ATIVO",
    "idsRecursosAcessibilidade": [1, 2, 3]
  }'
```

**Resposta (201 Created):**
```json
{
  "idEspaco": 1,
  "nome": "Auditório Principal",
  "descricao": "Auditório moderno com equipamentos de apresentação",
  "capacidade": 150,
  "urlCapa": "https://exemplo.com/img/auditorio.jpg",
  "status": "ATIVO",
  "endereco": {
    "idEndereco": 1,
    "rua": "Rua Principal",
    "numero": "500"
  },
  "tipoEspaco": {
    "idTipoEspaco": 1,
    "nome": "Auditório"
  },
  "recursosAcessibilidade": [
    {"idRecurso": 1, "nome": "Rampa de Acesso"},
    {"idRecurso": 2, "nome": "Elevador"},
    {"idRecurso": 3, "nome": "Banheiro Adaptado"}
  ]
}
```

---

### 8.2 - Listar Todos os Espaços

**Endpoint:** `GET /espacos`

```bash
curl -X GET http://localhost:8080/espacos
```

---

### 8.3 - Listar Espaços por Status

**Endpoint:** `GET /espacos/status/{status}`

```bash
curl -X GET http://localhost:8080/espacos/status/ATIVO
```

**Status Válidos:** `ATIVO`, `INTERDITADO`, `INATIVO`

---

### 8.4 - Consultar Espaço por ID

**Endpoint:** `GET /espacos/{id}`

```bash
curl -X GET http://localhost:8080/espacos/1
```

---

### 8.5 - Atualizar Espaço

**Endpoint:** `PUT /espacos/{id}`

```bash
curl -X PUT http://localhost:8080/espacos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "idEndereco": 1,
    "idTipoEspaco": 1,
    "capacidade": 200,
    "nome": "Auditório Principal - Expandido",
    "descricao": "Auditório ampliado com nova capacidade",
    "urlCapa": "https://exemplo.com/img/auditorio-novo.jpg",
    "status": "ATIVO",
    "idsRecursosAcessibilidade": [1, 2, 3, 4]
  }'
```

---

### 8.6 - Deletar Espaço

**Endpoint:** `DELETE /espacos/{id}`

```bash
curl -X DELETE http://localhost:8080/espacos/1
```

---

## 🛠️ MÓDULO 9: EQUIPAMENTOS

### 9.1 - Cadastrar Equipamento

**Endpoint:** `POST /equipamentos`

```bash
curl -X POST http://localhost:8080/equipamentos \
  -H "Content-Type: application/json" \
  -d '{
    "idEspaco": 1,
    "nome": "Projetor",
    "descricao": "Projetor Full HD com 3000 lumens",
    "qtd": 2
  }'
```

**Resposta (201 Created):**
```json
{
  "idEquipamento": 1,
  "espaco": {
    "idEspaco": 1,
    "nome": "Auditório Principal"
  },
  "nome": "Projetor",
  "descricao": "Projetor Full HD com 3000 lumens",
  "qtd": 2
}
```

---

### 9.2 - Listar Todos os Equipamentos

**Endpoint:** `GET /equipamentos`

```bash
curl -X GET http://localhost:8080/equipamentos
```

---

### 9.3 - Listar Equipamentos por Espaço

**Endpoint:** `GET /equipamentos/espaco/{idEspaco}`

```bash
curl -X GET http://localhost:8080/equipamentos/espaco/1
```

---

### 9.4 - Consultar Equipamento por ID

**Endpoint:** `GET /equipamentos/{id}`

```bash
curl -X GET http://localhost:8080/equipamentos/1
```

---

### 9.5 - Atualizar Equipamento

**Endpoint:** `PUT /equipamentos/{id}`

```bash
curl -X PUT http://localhost:8080/equipamentos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "idEspaco": 1,
    "nome": "Projetor 4K",
    "descricao": "Projetor 4K com 5000 lumens",
    "qtd": 3
  }'
```

---

### 9.6 - Deletar Equipamento

**Endpoint:** `DELETE /equipamentos/{id}`

```bash
curl -X DELETE http://localhost:8080/equipamentos/1
```

---

## ⚠️ MÓDULO 10: OCORRÊNCIAS

### 10.1 - Cadastrar Ocorrência

**Endpoint:** `POST /ocorrencias`

```bash
curl -X POST http://localhost:8080/ocorrencias \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Lâmpada Queimada",
    "descricao": "Lâmpada de emergência não funciona",
    "dtRegistro": "2024-01-15",
    "tipo": "MANUTENCAO",
    "idEspaco": 1,
    "status": "ABERTA"
  }'
```

**Resposta (201 Created):**
```json
{
  "idOcorrencia": 1,
  "titulo": "Lâmpada Queimada",
  "descricao": "Lâmpada de emergência não funciona",
  "dtRegistro": "2024-01-15",
  "tipo": "MANUTENCAO",
  "espaco": {
    "idEspaco": 1,
    "nome": "Auditório Principal"
  },
  "status": "ABERTA"
}
```

**Tipos Válidos:** `MANUTENCAO`, `DANO`, `LIMPEZA`, `SEGURANCA`, `OUTRO`  
**Status Válidos:** `ABERTA`, `EM_ANDAMENTO`, `RESOLVIDA`

**⚠️ IMPORTANTE:** Ao registrar uma ocorrência do tipo `MANUTENCAO`, o status do espaço é automaticamente alterado para `INTERDITADO`.

---

### 10.2 - Listar Todas as Ocorrências

**Endpoint:** `GET /ocorrencias`

```bash
curl -X GET http://localhost:8080/ocorrencias
```

---

### 10.3 - Listar Ocorrências por Espaço

**Endpoint:** `GET /ocorrencias/espaco/{idEspaco}`

```bash
curl -X GET http://localhost:8080/ocorrencias/espaco/1
```

---

### 10.4 - Consultar Ocorrência por ID

**Endpoint:** `GET /ocorrencias/{id}`

```bash
curl -X GET http://localhost:8080/ocorrencias/1
```

---

### 10.5 - Atualizar Ocorrência

**Endpoint:** `PUT /ocorrencias/{id}`

```bash
curl -X PUT http://localhost:8080/ocorrencias/1 \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Lâmpada de Emergência Queimada",
    "descricao": "Lâmpada localizada na saída de emergência",
    "dtRegistro": "2024-01-15",
    "tipo": "MANUTENCAO",
    "idEspaco": 1,
    "status": "EM_ANDAMENTO"
  }'
```

---

### 10.6 - Deletar Ocorrência

**Endpoint:** `DELETE /ocorrencias/{id}`

```bash
curl -X DELETE http://localhost:8080/ocorrencias/1
```

---

## 📅 MÓDULO 11: RESERVAS

### 11.1 - Cadastrar Reserva

**Endpoint:** `POST /reservas`

```bash
curl -X POST http://localhost:8080/reservas \
  -H "Content-Type: application/json" \
  -d '{
    "idSolicitante": 1,
    "idEspaco": 1,
    "horaInicio": "09:00",
    "dtUso": "2024-02-20",
    "horaFim": "12:00",
    "finalidade": "Reunião do projeto AgendaAlegre",
    "qtdPessoas": 50,
    "status": "DOCUMENTOS_PENDENTES"
  }'
```

**Resposta (201 Created):**
```json
{
  "idReserva": 1,
  "solicitante": {
    "idSolicitante": 1,
    "nome": "João Silva"
  },
  "espaco": {
    "idEspaco": 1,
    "nome": "Auditório Principal"
  },
  "horaInicio": "09:00",
  "dtUso": "2024-02-20",
  "horaFim": "12:00",
  "finalidade": "Reunião do projeto AgendaAlegre",
  "qtdPessoas": 50,
  "dtSolicitacao": "2024-01-15T10:30:00",
  "status": "DOCUMENTOS_PENDENTES",
  "dtCancelamento": null,
  "dtConfirmacao": null
}
```

**Status Válidos:** `DOCUMENTOS_PENDENTES`, `EM_ANALISE`, `APROVADA`, `REJEITADA`, `AGUARDANDO_TERMO`, `CONFIRMADA`, `CANCELADA`

**Validações Aplicadas:**
- ✅ Espaço deve estar ATIVO
- ✅ Data de uso deve ser no futuro
- ✅ Quantidade de pessoas ≤ capacidade do espaço
- ✅ Hora de fim > hora de início
- ✅ Não pode haver outra reserva APROVADA no mesmo período no mesmo espaço

---

### 11.2 - Listar Todas as Reservas

**Endpoint:** `GET /reservas`

```bash
curl -X GET http://localhost:8080/reservas
```

---

### 11.3 - Listar Reservas por Solicitante

**Endpoint:** `GET /reservas/solicitante/{idSolicitante}`

```bash
curl -X GET http://localhost:8080/reservas/solicitante/1
```

---

### 11.4 - Consultar Reserva por ID

**Endpoint:** `GET /reservas/{id}`

```bash
curl -X GET http://localhost:8080/reservas/1
```

---

### 11.5 - Atualizar Reserva

**Endpoint:** `PUT /reservas/{id}`

```bash
curl -X PUT http://localhost:8080/reservas/1 \
  -H "Content-Type: application/json" \
  -d '{
    "idSolicitante": 1,
    "idEspaco": 1,
    "horaInicio": "10:00",
    "dtUso": "2024-02-20",
    "horaFim": "13:00",
    "finalidade": "Reunião estratégica do projeto",
    "qtdPessoas": 60,
    "status": "EM_ANALISE"
  }'
```

---

### 11.6 - Deletar Reserva

**Endpoint:** `DELETE /reservas/{id}`

```bash
curl -X DELETE http://localhost:8080/reservas/1
```

---

## 📊 MÓDULO 12: ANÁLISE DE RESERVAS

### 12.1 - Cadastrar Análise de Reserva (Admin)

**Endpoint:** `POST /analises-reserva`

```bash
curl -X POST http://localhost:8080/analises-reserva \
  -H "Content-Type: application/json" \
  -d '{
    "observacao": "Reserva aprovada com base na documentação entregue",
    "dtAnalise": "2024-01-16",
    "idReserva": 1,
    "idAdministrador": 1
  }'
```

**Resposta (201 Created):**
```json
{
  "numAnalise": 1,
  "observacao": "Reserva aprovada com base na documentação entregue",
  "dtAnalise": "2024-01-16",
  "reserva": {
    "idReserva": 1,
    "finalidade": "Reunião do projeto"
  },
  "administrador": {
    "idAdministrador": 1,
    "nome": "Admin User"
  }
}
```

---

### 12.2 - Consultar Análise por Reserva

**Endpoint:** `GET /analises-reserva/reserva/{idReserva}`

```bash
curl -X GET http://localhost:8080/analises-reserva/reserva/1
```

---

### 12.3 - Consultar Análise por ID

**Endpoint:** `GET /analises-reserva/{id}`

```bash
curl -X GET http://localhost:8080/analises-reserva/1
```

---

### 12.4 - Atualizar Análise

**Endpoint:** `PUT /analises-reserva/{id}`

```bash
curl -X PUT http://localhost:8080/analises-reserva/1 \
  -H "Content-Type: application/json" \
  -d '{
    "observacao": "Reserva aprovada com condições especiais",
    "dtAnalise": "2024-01-16",
    "idReserva": 1,
    "idAdministrador": 1
  }'
```

---

### 12.5 - Deletar Análise

**Endpoint:** `DELETE /analises-reserva/{id}`

```bash
curl -X DELETE http://localhost:8080/analises-reserva/1
```

---

## ✍️ MÓDULO 13: TERMOS ACEITOS

### 13.1 - Cadastrar Termo Aceito

**Endpoint:** `POST /termos-aceito`

```bash
curl -X POST http://localhost:8080/termos-aceito \
  -H "Content-Type: application/json" \
  -d '{
    "idReserva": 1,
    "idModeloTermo": 1
  }'
```

**Resposta (201 Created):**
```json
{
  "numTermo": 1,
  "reserva": {
    "idReserva": 1,
    "finalidade": "Reunião do projeto"
  },
  "dtGeracao": "2024-01-16T15:30:00",
  "dtAceite": null,
  "modeloTermo": {
    "idModelo": 1,
    "titulo": "Termo de Responsabilidade"
  }
}
```

**Restrição:** Apenas reservas com status `APROVADA` podem ter termos aceitos.

---

### 13.2 - Consultar Termo por Reserva

**Endpoint:** `GET /termos-aceito/reserva/{idReserva}`

```bash
curl -X GET http://localhost:8080/termos-aceito/reserva/1
```

---

### 13.3 - Consultar Termo por ID

**Endpoint:** `GET /termos-aceito/{id}`

```bash
curl -X GET http://localhost:8080/termos-aceito/1
```

---

### 13.4 - Aceitar Termo (Assinar)

**Endpoint:** `PUT /termos-aceito/{id}/aceitar`

```bash
curl -X PUT http://localhost:8080/termos-aceito/1/aceitar
```

**Resposta (200 OK):**
```json
{
  "numTermo": 1,
  "reserva": {
    "idReserva": 1
  },
  "dtGeracao": "2024-01-16T15:30:00",
  "dtAceite": "2024-01-16T16:00:00",
  "modeloTermo": {
    "idModelo": 1
  }
}
```

---

### 13.5 - Deletar Termo

**Endpoint:** `DELETE /termos-aceito/{id}`

```bash
curl -X DELETE http://localhost:8080/termos-aceito/1
```

---

## ⭐ MÓDULO 14: AVALIAÇÕES

### 14.1 - Cadastrar Avaliação

**Endpoint:** `POST /avaliacoes`

```bash
curl -X POST http://localhost:8080/avaliacoes \
  -H "Content-Type: application/json" \
  -d '{
    "idReserva": 1,
    "nota": 5,
    "comentario": "Espaço excelente, muito bem equipado e localização perfeita!",
    "dtAvaliacao": "2024-02-21"
  }'
```

**Resposta (201 Created):**
```json
{
  "idAvaliacao": 1,
  "reserva": {
    "idReserva": 1,
    "finalidade": "Reunião do projeto"
  },
  "nota": 5,
  "comentario": "Espaço excelente, muito bem equipado e localização perfeita!",
  "dtAvaliacao": "2024-02-21"
}
```

**Restrições:**
- ✅ Apenas reservas com status `CONFIRMADA` podem ser avaliadas
- ✅ Nota deve estar entre 1 e 5
- ✅ Uma avaliação por reserva
- ✅ Comentário máximo 1000 caracteres

---

### 14.2 - Consultar Avaliação por Reserva

**Endpoint:** `GET /avaliacoes/reserva/{idReserva}`

```bash
curl -X GET http://localhost:8080/avaliacoes/reserva/1
```

---

### 14.3 - Consultar Avaliação por ID

**Endpoint:** `GET /avaliacoes/{id}`

```bash
curl -X GET http://localhost:8080/avaliacoes/1
```

---

### 14.4 - Atualizar Avaliação

**Endpoint:** `PUT /avaliacoes/{id}`

```bash
curl -X PUT http://localhost:8080/avaliacoes/1 \
  -H "Content-Type: application/json" \
  -d '{
    "idReserva": 1,
    "nota": 4,
    "comentario": "Muito bom! Apenas um ponto: precisa de mais ventilação.",
    "dtAvaliacao": "2024-02-21"
  }'
```

---

### 14.5 - Deletar Avaliação

**Endpoint:** `DELETE /avaliacoes/{id}`

```bash
curl -X DELETE http://localhost:8080/avaliacoes/1
```

---

## 🔍 EXEMPLO DE WORKFLOW COMPLETO

### Cenário: Criar uma reserva completa com análise, termo e avaliação

**Passo 1: Cadastrar Usuário (Solicitante)**

```bash
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "email": "maria@email.com",
    "cpf": "98765432100",
    "telefone": "85999999999",
    "ativo": true,
    "senha": "senha123"
  }'
# Salve o idUsuario retornado (ex: 5)
```

**Passo 2: Criar Reserva**

```bash
curl -X POST http://localhost:8080/reservas \
  -H "Content-Type: application/json" \
  -d '{
    "idSolicitante": 5,
    "idEspaco": 1,
    "horaInicio": "14:00",
    "dtUso": "2024-03-01",
    "horaFim": "16:00",
    "finalidade": "Workshop de Tecnologia",
    "qtdPessoas": 100,
    "status": "DOCUMENTOS_PENDENTES"
  }'
# Salve o idReserva retornado (ex: 10)
```

**Passo 3: Admin Analisa e Aprova Reserva**

```bash
curl -X POST http://localhost:8080/analises-reserva \
  -H "Content-Type: application/json" \
  -d '{
    "observacao": "Todos os documentos em ordem",
    "dtAnalise": "2024-02-24",
    "idReserva": 10,
    "idAdministrador": 1
  }'
```

**Passo 4: Atualizar Status da Reserva para APROVADA**

```bash
curl -X PUT http://localhost:8080/reservas/10 \
  -H "Content-Type: application/json" \
  -d '{
    "idSolicitante": 5,
    "idEspaco": 1,
    "horaInicio": "14:00",
    "dtUso": "2024-03-01",
    "horaFim": "16:00",
    "finalidade": "Workshop de Tecnologia",
    "qtdPessoas": 100,
    "status": "APROVADA"
  }'
```

**Passo 5: Criar Termo Aceito**

```bash
curl -X POST http://localhost:8080/termos-aceito \
  -H "Content-Type: application/json" \
  -d '{
    "idReserva": 10,
    "idModeloTermo": 1
  }'
# Salve o numTermo retornado (ex: 5)
```

**Passo 6: Aceitar Termo (Assinar)**

```bash
curl -X PUT http://localhost:8080/termos-aceito/5/aceitar
```

**Passo 7: Atualizar Reserva para CONFIRMADA**

```bash
curl -X PUT http://localhost:8080/reservas/10 \
  -H "Content-Type: application/json" \
  -d '{
    "idSolicitante": 5,
    "idEspaco": 1,
    "horaInicio": "14:00",
    "dtUso": "2024-03-01",
    "horaFim": "16:00",
    "finalidade": "Workshop de Tecnologia",
    "qtdPessoas": 100,
    "status": "CONFIRMADA",
    "dtConfirmacao": "2024-02-25T10:00:00"
  }'
```

**Passo 8: Após Evento, Deixar Avaliação**

```bash
curl -X POST http://localhost:8080/avaliacoes \
  -H "Content-Type: application/json" \
  -d '{
    "idReserva": 10,
    "nota": 5,
    "comentario": "Workshop foi excelente! Espaço amplo e bem equipado.",
    "dtAvaliacao": "2024-03-02"
  }'
```

---

## ⚠️ CÓDIGOS DE RESPOSTA HTTP

| Código | Significado |
|--------|------------|
| **200** | OK - Requisição bem-sucedida |
| **201** | Created - Recurso criado com sucesso |
| **204** | No Content - Sucesso (sem corpo de resposta) |
| **400** | Bad Request - Erro na validação de dados |
| **404** | Not Found - Recurso não encontrado |
| **500** | Internal Server Error - Erro no servidor |

---

## 🐛 TROUBLESHOOTING

### Erro: "Espaço deve estar ATIVO"
**Solução:** Verifique se o espaço tem status `ATIVO`. Se foi interditado por manutenção, atualize o status.

### Erro: "Já existe outra reserva aprovada para este período"
**Solução:** Escolha outro horário ou espaço para a reserva.

### Erro: "Termo pode ser aceito apenas para reserva aprovada"
**Solução:** Mude o status da reserva para `APROVADA` antes de criar o termo.

### Erro: "Apenas reservas confirmadas podem ser avaliadas"
**Solução:** Confirme a reserva (status `CONFIRMADA`) antes de deixar avaliação.

### Erro: "Nota deve ser um valor entre 1 e 5"
**Solução:** Use uma nota de 1 a 5, ex: `"nota": 4`

---

## 📊 DICAS DE TESTE

### Usar Postman:
1. Importe os exemplos acima como requests
2. Use a aba "Pre-request Script" para gerar timestamps dinâmicos
3. Salve IDs de resposta em variáveis da collection

### Usar cURL (Linha de Comando):
```bash
# Testar PUT com JSON formatado
curl -X PUT http://localhost:8080/recursos-1 \
  -H "Content-Type: application/json" \
  -d @arquivo.json
```

### Salvar Response:
```bash
curl http://localhost:8080/espacos > espacos.json
```

---

**Fim do Guia de Testes**
