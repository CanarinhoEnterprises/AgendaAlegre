# 🔗 REFERÊNCIA RÁPIDA - ENDPOINTS AGENDAALEGRE

## 📊 Resumo Visual de Todos os CRUDs

| # | Módulo | C | R | U | D | Total | Status |
|---|--------|---|---|---|---|-------|--------|
| 1 | Usuários | POST | GET | PUT | DELETE | 5 | ✅ FIXADO |
| 2 | Cidades | POST | GET | PUT | DELETE | 5 | ✅ |
| 3 | Endereços | POST | GET | PUT | DELETE | 5 | ✅ |
| 4 | Tipos Espaço | POST | GET | PUT | DELETE | 5 | ✅ |
| 5 | Tipos Documento | POST | GET | PUT | DELETE | 5 | ✅ |
| 6 | Modelos Termo | POST | GET | PUT | DELETE | 5 | ✅ |
| 7 | Espaços | POST | GET+ | PUT | DELETE | 6 | ✅ |
| 8 | Equipamentos | POST | GET+ | PUT | DELETE | 6 | ✅ |
| 9 | Ocorrências | POST | GET+ | PUT | DELETE | 6 | ✅ |
| 10 | Análise Reserva | POST | GET+ | PUT | DELETE | 5 | ✅ |
| 11 | Termos Aceitos | POST | GET+ | PUT+ | DELETE | 5 | ✅ |
| 12 | Avaliações | POST | GET+ | PUT | DELETE | 5 | ✅ |
| 13 | Reservas | POST | GET+ | PUT | DELETE | 6 | ✅ |
| 14 | Recursos Acesso | POST | GET | PUT | DELETE | 5 | ✅ |
| | | | | | | **75+** | **✅ OK** |

---

## 📋 Listagem Completa de Endpoints

### 1️⃣ USUÁRIOS

```
POST   /usuarios
GET    /usuarios
GET    /usuarios/{id}
PUT    /usuarios/{id}
DELETE /usuarios/{id}
```

### 2️⃣ CIDADES

```
POST   /cidades
GET    /cidades
GET    /cidades/{id}
PUT    /cidades/{id}
DELETE /cidades/{id}
```

### 3️⃣ ENDEREÇOS

```
POST   /enderecos
GET    /enderecos
GET    /enderecos/{id}
PUT    /enderecos/{id}
DELETE /enderecos/{id}
```

### 4️⃣ TIPOS DE ESPAÇO

```
POST   /tipos-espaco
GET    /tipos-espaco
GET    /tipos-espaco/{id}
PUT    /tipos-espaco/{id}
DELETE /tipos-espaco/{id}
```

### 5️⃣ TIPOS DE DOCUMENTO

```
POST   /tipos-doc
GET    /tipos-doc
GET    /tipos-doc/{id}
PUT    /tipos-doc/{id}
DELETE /tipos-doc/{id}
```

### 6️⃣ MODELOS DE TERMO

```
POST   /modelos-termo
GET    /modelos-termo
GET    /modelos-termo/{id}
PUT    /modelos-termo/{id}
DELETE /modelos-termo/{id}
```

### 7️⃣ RECURSOS DE ACESSIBILIDADE

```
POST   /recursos-acessibilidade
GET    /recursos-acessibilidade
GET    /recursos-acessibilidade/{id}
PUT    /recursos-acessibilidade/{id}
DELETE /recursos-acessibilidade/{id}
```

### 8️⃣ ESPAÇOS

```
POST   /espacos
GET    /espacos
GET    /espacos/status/{status}        ← ATIVO, INTERDITADO, INATIVO
GET    /espacos/{id}
PUT    /espacos/{id}
DELETE /espacos/{id}
```

### 9️⃣ EQUIPAMENTOS

```
POST   /equipamentos
GET    /equipamentos
GET    /equipamentos/espaco/{idEspaco}
GET    /equipamentos/{id}
PUT    /equipamentos/{id}
DELETE /equipamentos/{id}
```

### 🔟 OCORRÊNCIAS

```
POST   /ocorrencias
GET    /ocorrencias
GET    /ocorrencias/espaco/{idEspaco}
GET    /ocorrencias/{id}
PUT    /ocorrencias/{id}
DELETE /ocorrencias/{id}
```

### 1️⃣1️⃣ ANÁLISES DE RESERVA

```
POST   /analises-reserva
GET    /analises-reserva/reserva/{idReserva}
GET    /analises-reserva/{id}
PUT    /analises-reserva/{id}
DELETE /analises-reserva/{id}
```

### 1️⃣2️⃣ TERMOS ACEITOS

```
POST   /termos-aceito
GET    /termos-aceito/reserva/{idReserva}
GET    /termos-aceito/{id}
PUT    /termos-aceito/{id}/aceitar    ← Assinatura
DELETE /termos-aceito/{id}
```

### 1️⃣3️⃣ AVALIAÇÕES

```
POST   /avaliacoes
GET    /avaliacoes/reserva/{idReserva}
GET    /avaliacoes/{id}
PUT    /avaliacoes/{id}
DELETE /avaliacoes/{id}
```

### 1️⃣4️⃣ RESERVAS (Mais Complexo)

```
POST   /reservas
GET    /reservas
GET    /reservas/solicitante/{idSolicitante}
GET    /reservas/{id}
PUT    /reservas/{id}
DELETE /reservas/{id}
```

---

## 🎯 Quick Start - Cenário Prático

### Criar usuário e fazer reserva em 3 comandos:

**1. Registrar com acesso:**
```bash
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Maria", "email":"maria@ex.com", "cpf":"12345678900", "telefone":"85999999999", "ativo":true, "senha":"123"}'
```

**2. Criar reserva (substitua IDs):**
```bash
curl -X POST http://localhost:8080/reservas \
  -H "Content-Type: application/json" \
  -d '{"idSolicitante":1, "idEspaco":1, "horaInicio":"14:00", "dtUso":"2024-03-01", "horaFim":"16:00", "finalidade":"Reunião", "qtdPessoas":20, "status":"DOCUMENTOS_PENDENTES"}'
```

**3. Listar minhas reservas:**
```bash
curl http://localhost:8080/reservas/solicitante/1
```

---

## 🔍 Status HTTP Esperados

| HTTP Code | Cenário |
|-----------|---------|
| **200 OK** | GET, PUT bem-sucedido |
| **201 Created** | POST bem-sucedido |
| **204 No Content** | DELETE bem-sucedido |
| **400 Bad Request** | Validação falhou (capacidade < pessoas, data no passado, etc) |
| **404 Not Found** | Recurso não encontrado (ID inválido) |
| **500 Server Error** | Erro no backend |

---

## 💾 Formatos de Resposta

### Sucesso (200/201)

```json
{
  "idRecurso": 1,
  "nome": "João",
  "ativo": true
}
```

### Erro (400)

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Quantidade deve ser maior que 0",
  "path": "/equipamentos"
}
```

### Não encontrado (404)

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Usuário não encontrado",
  "path": "/usuarios/999"
}
```

---

## 🚀 Padrões de URL

| Padrão | Exemplo | Uso |
|--------|---------|-----|
| `/recurso` | `/espacos` | Listar todos |
| `/recurso/{id}` | `/espacos/5` | Consultar específico |
| `/recurso/tipo/{tipo}` | `/espacos/status/ATIVO` | Filtrar por atributo |
| `/recurso1/{id}/recurso2` | `/espacos/5/equipamentos` | Nested resources |
| `/recurso/{id}/acao` | `/termos-aceito/5/aceitar` | Ação customizada |

---

## 📝 Headers Padrões

```
Content-Type: application/json
Accept: application/json
```

---

## 🧪 Comandos Úteis cURL

### Listar com pretty-print:
```bash
curl http://localhost:8080/espacos | jq
```

### Salvar resposta em arquivo:
```bash
curl http://localhost:8080/espacos > espacos.json
```

### Debugar com headers verbosos:
```bash
curl -v http://localhost:8080/espacos
```

### Usar arquivo JSON para POST:
```bash
curl -X POST http://localhost:8080/espacos \
  -H "Content-Type: application/json" \
  -d @espaco.json
```

### Verificar apenas headers de resposta:
```bash
curl -I http://localhost:8080/espacos
```

---

## 🔐 Campos Sensíveis

| Campo | Nunca Retorna | Sempre Hash |
|-------|---|---|
| `usuario.senha` | ✅ | ⚠️ (não está) |
| `solicitante.cpf` | ❌ | ❌ |
| `administrador.*` | ❌ | ❌ |

**⚠️ IMPORTANTE:** Senhas NÃO estão sendo criptografadas. Implementar BCrypt é crítico para produção.

---

## ⏱️ Tempo de Resposta Esperado

| Operação | Tempo Esperado |
|----------|---|
| GET simples | < 100ms |
| POST/PUT com validação | < 200ms |
| Listagem com 1000+ registros | < 500ms |
| DELETE com cascata | < 300ms |
| Query com JOIN | < 250ms |

---

## 🔄 Fluxo De Status De Reserva

```
DOCUMENTOS_PENDENTES
        ↓
    EM_ANALISE (admin analisa)
        ↓
    ├─→ APROVADA (aceito)
    │   ├─→ AGUARDANDO_TERMO (termo pendente)
    │   │   └─→ CONFIRMADA (termo assinado + data confirmada)
    │   │       └─→ [AVALIACAO disponível após evento]
    │   │
    │   └─→ CANCELADA (solicitante cancela)
    │
    └─→ REJEITADA (admin rejeita)
```

---

## 🎯 Validações Por Módulo

| Módulo | Principais Validações |
|--------|---|
| **Usuário** | Email único, senha obrigatória |
| **Espaço** | Capacidade > 0, status deve ser válido |
| **Reserva** | Data futuro, qtd ≤ capacidade, sem conflito temporal |
| **Equipamento** | Qtd ≥ 0, espaço deve existir |
| **Ocorrência** | Tipo válido, data registro, RN33 (MANUTENCAO→interdita) |
| **TermoAceito** | Reserva deve ser APROVADA, max 1 por reserva |
| **Avaliação** | Reserva deve ser CONFIRMADA, nota 1-5, max 1 por reserva |
| **AnaliseReserva** | Reserva e admin devem existir |

---

## 📊 Dados de Exemplo Para Teste

### Usuário
```json
{
  "nome": "João Silva",
  "email": "joao@example.com",
  "cpf": "12345678900",
  "telefone": "85987654321",
  "ativo": true,
  "senha": "senha123"
}
```

### Espaço
```json
{
  "idEndereco": 1,
  "idTipoEspaco": 1,
  "capacidade": 150,
  "nome": "Auditório Principal",
  "descricao": "Espaço amplo com equipamentos",
  "urlCapa": "https://example.com/img.jpg",
  "status": "ATIVO",
  "idsRecursosAcessibilidade": [1, 2, 3]
}
```

### Reserva
```json
{
  "idSolicitante": 1,
  "idEspaco": 1,
  "horaInicio": "09:00",
  "dtUso": "2024-02-20",
  "horaFim": "12:00",
  "finalidade": "Workshop",
  "qtdPessoas": 80,
  "status": "DOCUMENTOS_PENDENTES"
}
```

### Avaliação
```json
{
  "idReserva": 1,
  "nota": 5,
  "comentario": "Excelente espaço e muito bem equipado!",
  "dtAvaliacao": "2024-02-21"
}
```

---

## 🉐 Enumerações Válidas

### StatusEspaco
```
ATIVO
INTERDITADO
INATIVO
```

### StatusReserva
```
DOCUMENTOS_PENDENTES
EM_ANALISE
APROVADA
REJEITADA
AGUARDANDO_TERMO
CONFIRMADA
CANCELADA
```

### StatusOcorrencia
```
ABERTA
EM_ANDAMENTO
RESOLVIDA
```

### TipoOcorrencia
```
MANUTENCAO     ← Causa auto-interdição do espaço
DANO
LIMPEZA
SEGURANCA
OUTRO
```

---

## 🔑 Campos Obrigatórios

| Recurso | Campos Obrigatórios |
|---------|---|
| **Usuario** | nome, email, cpf ou cnpj, telefone, ativo, senha |
| **Espaço** | idEndereco, idTipoEspaco, capacidade, nome, status |
| **Reserva** | idSolicitante, idEspaco, horaInicio, dtUso, horaFim, finalidade, qtdPessoas |
| **Avaliação** | idReserva, nota, comentario |
| **TermoAceito** | idReserva |
| **Ocorrência** | titulo, descricao, dtRegistro, tipo, idEspaco |
| **Equipamento** | idEspaco, nome, descricao, qtd |

---

## 📱 Endpoints Por Caso de Uso

### Cenário: Buscar espaços disponíveis
```bash
GET /espacos/status/ATIVO
```

### Cenário: Fazer uma reserva
```bash
POST /reservas
```

### Cenário: Acompanhar minha reserva
```bash
GET /reservas/solicitante/{meuId}
```

### Cenário: Registrar problema no espaço
```bash
POST /ocorrencias
```

### Cenário: Deixar feedback
```bash
POST /avaliacoes
```

### Cenário: Gerenciar espaços (admin)
```bash
POST /espacos
GET /espacos
PUT /espacos/{id}
DELETE /espacos/{id}
POST /equipamentos
GET /ocorrencias
POST /analises-reserva
```

---

## ✅ Checklist De Teste

- [ ] POST user com email duplicado → erro 400
- [ ] POST reserva data passada → erro 400
- [ ] POST reserva quantidade > capacidade → erro 400
- [ ] POST ocorrência tipo MANUTENCAO → espaço fica INTERDITADO
- [ ] POST termo para reserva não-APROVADA → erro 400
- [ ] POST avaliação para reserva não-CONFIRMADA → erro 400
- [ ] PUT termo/{id}/aceitar → dtAceite preenchido
- [ ] GET /espacos/status/ATIVO → lista apenas ATIVO
- [ ] DELETE usuario → remove Solicitante e Administrador primeiro
- [ ] GET Usuario → senha não aparece em resposta

---

**Guia Completo:** [GUIA_TESTES_POSTMAN.md](GUIA_TESTES_POSTMAN.md)  
**Arquitetura:** [ARQUITETURA_TECNICA.md](ARQUITETURA_TECNICA.md)  
**Resumo Detalhado:** [RESUMO_IMPLEMENTACAO.md](RESUMO_IMPLEMENTACAO.md)
