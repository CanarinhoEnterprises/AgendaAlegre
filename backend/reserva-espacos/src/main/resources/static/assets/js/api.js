
const API_URL = "http://localhost:8081";

async function post(url, dados){

    const resposta = await fetch(API_URL + url, {
        method: "POST",
        headers:{
            "Content-Type":"application/json"
        },
        body: JSON.stringify(dados)
    });

    return resposta;
}

async function put(url, dados){

    const resposta = await fetch(API_URL + url, {
        method: "PUT",
        credentials: "include",
        headers:{
            "Content-Type":"application/json"
        },
        body: JSON.stringify(dados)
    });

    return resposta;
}

async function patch(url, dados){

    const resposta = await fetch(API_URL + url, {
        method: "PATCH",
        credentials: "include",
        headers:{
            "Content-Type":"application/json"
        },
        body: JSON.stringify(dados)
    });

    return resposta;
}

async function get(url){

    return fetch(API_URL + url, {
        method: "GET",
        credentials: "include",
        headers: {
            "Content-Type": "application/json"
        }
    });
}

async function del(url){

    return fetch(API_URL + url, {
        method: "DELETE",
        credentials: "include",
        headers: {
            "Content-Type": "application/json"
        }
    });
}

/*
    Lê o corpo de uma resposta com segurança (nem toda resposta
    de erro do backend retorna JSON válido).
*/
async function lerCorpo(resposta){

    const texto = await resposta.text();

    if (!texto) {
        return null;
    }

    try {
        return JSON.parse(texto);
    } catch (erro) {
        return null;
    }
}

/*
    Extrai uma mensagem de erro amigável do corpo da resposta,
    com um texto padrão de fallback. O backend padroniza o corpo
    de erro como {"mensagem": "..."} (ver GlobalExceptionHandler).
*/
async function mensagemDeErro(resposta, padrao){

    const corpo = await lerCorpo(resposta);

    if (corpo && (corpo.mensagem || corpo.message || corpo.erro)) {
        return corpo.mensagem || corpo.message || corpo.erro;
    }

    return padrao;
}

/* ==========================================================
   Auth / Usuário
========================================================== */

async function apiLogin(email, senha){

    const resposta = await post("/auth/login", { email: email, senha: senha });

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Email ou senha inválidos."));
    }

    return lerCorpo(resposta);
}

async function apiCadastrarUsuario(dados){

    const resposta = await post("/usuarios", dados);

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível concluir o cadastro."));
    }

    return lerCorpo(resposta);
}

/* ==========================================================
   Espaços
========================================================== */

async function apiListarEspacos(){

    const resposta = await get("/espacos");

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível carregar os espaços."));
    }

    return lerCorpo(resposta);
}

async function apiBuscarEspaco(idEspaco){

    const resposta = await get("/espacos/" + idEspaco);

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível carregar o espaço."));
    }

    return lerCorpo(resposta);
}

async function apiCadastrarEspaco(dados){

    const resposta = await post("/espacos", dados);

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível cadastrar o espaço."));
    }

    return lerCorpo(resposta);
}

async function apiListarTiposEspaco(){

    const resposta = await get("/tipos-espaco");

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível carregar os tipos de espaço."));
    }

    return lerCorpo(resposta);
}

async function apiListarRecursosAcessibilidadeCombo(){

    const resposta = await get("/recursos-acessibilidade/combo");

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível carregar os recursos de acessibilidade."));
    }

    return lerCorpo(resposta);
}

/* ==========================================================
   Endereços / Cidades (apoio ao cadastro de espaço)
========================================================== */

async function apiListarEnderecos(){

    const resposta = await get("/enderecos");
    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível carregar os endereços."));
    }

    return lerCorpo(resposta);
}

async function apiCadastrarEndereco(dados){

    const resposta = await post("/enderecos", dados);

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível cadastrar o endereço."));
    }

    return lerCorpo(resposta);
}

async function apiListarCidades(){

    const resposta = await get("/cidades");

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível carregar as cidades."));
    }

    return lerCorpo(resposta);
}

/* ==========================================================
   Reservas
========================================================== */

async function apiSolicitarReserva(dados){

    const resposta = await post("/reservas", dados);

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível solicitar a reserva."));
    }

    return lerCorpo(resposta);
}

async function apiListarReservas(){

    const resposta = await get("/reservas");

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível carregar as reservas."));
    }

    return lerCorpo(resposta);
}

async function apiListarReservasPorSolicitante(idSolicitante){

    const resposta = await get("/reservas/solicitante/" + idSolicitante);

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível carregar suas reservas."));
    }

    return lerCorpo(resposta);
}

async function apiListarReservasPorStatus(status){

    const resposta = await get("/reservas/status/" + status);

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível carregar as solicitações."));
    }

    return lerCorpo(resposta);
}

/*
    Cancela a reserva preservando o histórico (a API não realiza
    exclusão física neste fluxo — apenas altera o status para
    CANCELADA através do PATCH /reservas/{id}/cancelar).
*/
async function apiCancelarReserva(idReserva, idSolicitante, motivo){

    const resposta = await patch("/reservas/" + idReserva + "/cancelar", {
        idSolicitante: idSolicitante,
        motivo: motivo || null
    });

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível cancelar a reserva."));
    }

    return lerCorpo(resposta);
}

/* ==========================================================
   Análise de reservas (administrador)
========================================================== */

async function apiAprovarReserva(idReserva, idAdministrador, observacao){

    const resposta = await post("/analises-reserva", {
        idReserva: idReserva,
        idAdministrador: idAdministrador,
        aprovado: true,
        observacao: observacao || null
    });

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível aprovar a reserva."));
    }

    return lerCorpo(resposta);
}

async function apiRejeitarReserva(idReserva, idAdministrador, motivo){

    const resposta = await post("/analises-reserva", {
        idReserva: idReserva,
        idAdministrador: idAdministrador,
        aprovado: false,
        observacao: motivo
    });

    if (!resposta.ok) {
        throw new Error(await mensagemDeErro(resposta, "Não foi possível rejeitar a reserva."));
    }

    return lerCorpo(resposta);
}