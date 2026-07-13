/* ==========================================================
   utils.js
   Funções utilitárias compartilhadas entre as páginas do
   Agenda Alegre (sessão do usuário, formatação, validação).
========================================================== */

const CHAVE_USUARIO = "agendaAlegre.usuario";

/* ---------- Sessão do usuário ---------- */

function salvarUsuarioLogado(usuario) {
    localStorage.setItem(CHAVE_USUARIO, JSON.stringify(usuario));
}

function obterUsuarioLogado() {
    const bruto = localStorage.getItem(CHAVE_USUARIO);

    if (!bruto) {
        return null;
    }

    try {
        return JSON.parse(bruto);
    } catch (erro) {
        return null;
    }
}

/*
    Retorna o id do solicitante/administrador logado. Como as tabelas
    solicitante e administrador compartilham a mesma chave primária de
    usuario (idUsuario), o id retornado pelo login (LoginResponseDTO.id)
    já corresponde ao idSolicitante ou idAdministrador.
*/
function obterIdUsuarioLogado() {
    const usuario = obterUsuarioLogado();
    return usuario ? usuario.id : null;
}

function estaLogado() {
    return obterUsuarioLogado() !== null;
}

function ehAdministrador() {
    const usuario = obterUsuarioLogado();
    return !!usuario && usuario.tipo === "ADMINISTRADOR";
}

function encerrarSessao() {
    localStorage.removeItem(CHAVE_USUARIO);
}

/*
    Protege páginas que exigem login (e, opcionalmente, um tipo
    específico de usuário). Redireciona para a página correta
    quando o acesso não é permitido.
*/
function protegerPagina(tipoExigido) {

    const usuario = obterUsuarioLogado();

    if (!usuario) {
        window.location.href = caminhoRelativoLogin();
        return null;
    }

    if (tipoExigido && usuario.tipo !== tipoExigido) {
        window.location.href = caminhoRelativoLogin();
        return null;
    }

    return usuario;
}

function caminhoRelativoLogin() {

    const caminho = window.location.pathname;

    if (caminho.includes("/pages/usuario/") || caminho.includes("/pages/admin/")) {
        return "../login.html";
    }

    return "login.html";
}

/* ---------- Formatação ---------- */

function formatarDataBr(dataIso) {

    if (!dataIso) {
        return "";
    }

    const [ano, mes, dia] = dataIso.split("-");
    return dia + "/" + mes + "/" + ano;
}

function formatarHora(hora) {

    if (!hora) {
        return "";
    }

    return hora.length >= 5 ? hora.substring(0, 5) : hora;
}

function escaparHtml(texto) {

    if (texto === null || texto === undefined) {
        return "";
    }

    const div = document.createElement("div");
    div.textContent = String(texto);
    return div.innerHTML;
}

function dataMinimaReserva() {

    const amanha = new Date();
    amanha.setDate(amanha.getDate() + 1);

    const ano = amanha.getFullYear();
    const mes = String(amanha.getMonth() + 1).padStart(2, "0");
    const dia = String(amanha.getDate()).padStart(2, "0");

    return ano + "-" + mes + "-" + dia;
}

/* ---------- Validações do lado do cliente ---------- */

/*
    Valida os dados de uma solicitação de reserva antes de enviar ao
    backend. Os nomes dos campos seguem exatamente o CadastroReservaDTO
    da API (idEspaco, dtUso, horaInicio, horaFim, finalidade, qtdPessoas).
*/
function validarDadosReserva(dados, capacidadeEspaco) {

    const erros = [];

    if (!dados.idEspaco) {
        erros.push("Selecione um espaço.");
    }

    if (!dados.dtUso) {
        erros.push("Informe a data da reserva.");
    } else if (dados.dtUso <= dataAtualIso()) {
        erros.push("A reserva só pode ser feita para uma data futura.");
    }

    if (!dados.horaInicio) {
        erros.push("Informe o horário de início.");
    }

    if (!dados.horaFim) {
        erros.push("Informe o horário de término.");
    }

    if (dados.horaInicio && dados.horaFim && dados.horaFim <= dados.horaInicio) {
        erros.push("O horário de término deve ser posterior ao horário de início.");
    }

    if (!dados.finalidade || dados.finalidade.trim().length === 0) {
        erros.push("Informe a finalidade da reserva.");
    }

    const quantidade = Number(dados.qtdPessoas);

    if (!dados.qtdPessoas || isNaN(quantidade) || quantidade <= 0) {
        erros.push("A quantidade de participantes deve ser maior que zero.");
    } else if (capacidadeEspaco && quantidade > capacidadeEspaco) {
        erros.push("A quantidade de participantes ultrapassa a capacidade do espaço (" + capacidadeEspaco + ").");
    }

    return erros;
}

/* ---------- Ícones e status (compartilhados) ---------- */

const ESPACO_ICONE_PADRAO =
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.4" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="7" width="18" height="13" rx="2"/><path d="M8 7V5a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/></svg>';

const ESPACO_STATUS_LABELS = {
    ATIVO: "Disponível",
    INTERDITADO: "Interditado",
    INATIVO: "Indisponível"
};

/*
    Status reais definidos em StatusReserva (backend). Os rótulos e
    classes visuais são mapeados para o CSS já existente no projeto
    (status-pendente, status-confirmada, status-concluida, status-cancelada).
*/
const RESERVA_STATUS_LABELS = {
    DOCUMENTOS_PENDENTES: "Documentos pendentes",
    EM_ANALISE: "Em análise",
    APROVADA: "Aprovada",
    REJEITADA: "Rejeitada",
    AGUARDANDO_TERMO: "Aguardando termo",
    CONFIRMADA: "Confirmada",
    CANCELADA: "Cancelada"
};

const RESERVA_STATUS_CLASSES = {
    DOCUMENTOS_PENDENTES: "status-pendente",
    EM_ANALISE: "status-pendente",
    APROVADA: "status-confirmada",
    AGUARDANDO_TERMO: "status-pendente",
    CONFIRMADA: "status-confirmada",
    REJEITADA: "status-cancelada",
    CANCELADA: "status-cancelada"
};

function dataAtualIso() {

    const hoje = new Date();

    const ano = hoje.getFullYear();
    const mes = String(hoje.getMonth() + 1).padStart(2, "0");
    const dia = String(hoje.getDate()).padStart(2, "0");

    return ano + "-" + mes + "-" + dia;
}

/*
    Monta uma descrição curta de localização a partir do endereço
    retornado pelo backend (Espaco.endereco).
*/
function montarLocalizacao(endereco) {

    if (!endereco) {
        return "";
    }

    const partes = [];

    if (endereco.bairro) {
        partes.push(endereco.bairro);
    }

    if (endereco.cidade && endereco.cidade.nome) {
        partes.push(endereco.cidade.nome + (endereco.cidade.UF ? "/" + endereco.cidade.UF : ""));
    }

    return partes.join(" - ");
}
