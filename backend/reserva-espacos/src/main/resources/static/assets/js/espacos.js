/* ==========================================================
   espacos.js
   Listagem de espaços públicos (usada em espaco.html e em
   espacoLogado.html) + abertura do formulário de solicitação
   de reserva. Os dados vêm exclusivamente de GET /espacos.
========================================================== */

(function () {

    const grid = document.getElementById("espacosGrid");

    if (!grid) {
        return;
    }

    const nota = document.getElementById("espacosNota");
    const filtrosWrap = document.querySelector(".filtros-wrap .filtros");

    const usuario = obterUsuarioLogado();
    const paginaLogada = window.location.pathname.includes("/pages/usuario/") ||
                         window.location.pathname.includes("/pages/admin/");

    let espacos = [];
    let tipoAtivo = "todos";

    async function carregarEspacos() {

        grid.innerHTML = '<p class="espacos-nota">Carregando espaços...</p>';

        try {
            const dados = await apiListarEspacos();
            espacos = Array.isArray(dados) ? dados : [];

            if (nota) {
                nota.style.display = "none";
            }

            montarFiltros();
            renderizar();

        } catch (erro) {
            grid.innerHTML = "";

            const mensagem = document.createElement("div");
            mensagem.className = "sem-espacos";
            mensagem.innerHTML = "<h3>Não foi possível carregar os espaços.</h3><p>" + escaparHtml(erro.message) + "</p>";
            grid.appendChild(mensagem);

            if (typeof notificar === "function") {
                notificar(erro.message, "erro");
            }
        }
    }

    /*
        Os filtros são construídos dinamicamente a partir dos tipos de
        espaço (TipoEspaco) que vieram do backend, já que não existe
        uma categoria fixa no modelo de dados.
    */
    function montarFiltros() {

        if (!filtrosWrap) {
            return;
        }

        const tipos = [];
        const vistos = new Set();

        espacos.forEach(function (espaco) {
            if (espaco.tipoEspaco && !vistos.has(espaco.tipoEspaco.idTipoEspaco)) {
                vistos.add(espaco.tipoEspaco.idTipoEspaco);
                tipos.push(espaco.tipoEspaco);
            }
        });

        filtrosWrap.innerHTML = "";

        const botaoTodos = document.createElement("button");
        botaoTodos.className = "filtro-btn active";
        botaoTodos.dataset.tipo = "todos";
        botaoTodos.textContent = "Todos";
        botaoTodos.addEventListener("click", function () { selecionarFiltro("todos"); });
        filtrosWrap.appendChild(botaoTodos);

        tipos.forEach(function (tipo) {
            const botao = document.createElement("button");
            botao.className = "filtro-btn";
            botao.dataset.tipo = String(tipo.idTipoEspaco);
            botao.textContent = tipo.nome;
            botao.addEventListener("click", function () { selecionarFiltro(String(tipo.idTipoEspaco)); });
            filtrosWrap.appendChild(botao);
        });
    }

    function selecionarFiltro(tipo) {

        tipoAtivo = tipo;

        filtrosWrap.querySelectorAll(".filtro-btn").forEach(function (botao) {
            botao.classList.toggle("active", botao.dataset.tipo === tipo);
        });

        renderizar();
    }

    function espacosFiltrados() {

        if (tipoAtivo === "todos") {
            return espacos;
        }

        return espacos.filter(function (espaco) {
            return espaco.tipoEspaco && String(espaco.tipoEspaco.idTipoEspaco) === tipoAtivo;
        });
    }

    function renderizar() {

        const lista = espacosFiltrados();

        grid.innerHTML = "";

        if (lista.length === 0) {
            const vazio = document.createElement("div");
            vazio.className = "sem-espacos";
            vazio.innerHTML = "<h3>Nenhum espaço encontrado.</h3><p>Tente outro filtro.</p>";
            grid.appendChild(vazio);
            return;
        }

        lista.forEach(function (espaco) {
            grid.appendChild(criarCardEspaco(espaco));
        });
    }

    function criarCardEspaco(espaco) {

        const disponivel = espaco.status === "ATIVO";
        const classeStatus = disponivel ? "status-disponivel" : "status-indisponivel";
        const rotuloStatus = ESPACO_STATUS_LABELS[espaco.status] || espaco.status;
        const localizacao = montarLocalizacao(espaco.endereco);
        const nomeTipo = espaco.tipoEspaco ? espaco.tipoEspaco.nome : "";

        const card = document.createElement("article");
        card.className = "espaco-card";
        card.dataset.espacoId = espaco.idEspaco;

        card.innerHTML =
            '<div class="espaco-imagem">' +
                (espaco.urlCapa
                    ? '<img src="' + escaparHtml(espaco.urlCapa) + '" alt="' + escaparHtml(espaco.nome) + '">'
                    : ESPACO_ICONE_PADRAO) +
            '</div>' +
            '<div class="espaco-info">' +
                (nomeTipo ? '<span class="espaco-categoria">' + escaparHtml(nomeTipo) + '</span>' : '') +
                '<h3>' + escaparHtml(espaco.nome) + '</h3>' +
                '<div class="espaco-local">' + escaparHtml(localizacao) + '</div>' +
                '<div class="espaco-rodape">' +
                    '<span class="status ' + classeStatus + '">' + escaparHtml(rotuloStatus) + '</span>' +
                    '<span class="acao-espaco"></span>' +
                '</div>' +
            '</div>';

        const areaAcao = card.querySelector(".acao-espaco");

        if (!paginaLogada) {

            const link = document.createElement("a");
            link.href = "login.html";
            link.className = "btn-reservar";
            link.textContent = "Entrar para reservar";
            areaAcao.appendChild(link);

        } else if (ehAdministrador()) {

            // Administrador apenas consulta os espaços nesta tela.

        } else if (!disponivel) {

            const botao = document.createElement("button");
            botao.type = "button";
            botao.className = "btn-reservar btn-desabilitado";
            botao.disabled = true;
            botao.textContent = "Indisponível";
            areaAcao.appendChild(botao);

        } else {

            const botao = document.createElement("button");
            botao.type = "button";
            botao.className = "btn-reservar";
            botao.textContent = "Reservar";

            botao.addEventListener("click", function () {
                abrirModalReserva(espaco);
            });

            areaAcao.appendChild(botao);
        }

        return card;
    }

    carregarEspacos();

    /* ====================================================
       Modal de solicitação de reserva
    ==================================================== */

    let modalReserva = null;

    function garantirModalReserva() {

        if (modalReserva) {
            return modalReserva;
        }

        const overlay = document.createElement("div");
        overlay.className = "modal-overlay";
        overlay.id = "modal-reserva";

        overlay.innerHTML =
            '<div class="modal-caixa">' +
                '<button type="button" class="modal-fechar" aria-label="Fechar">&times;</button>' +
                '<h2>Solicitar reserva</h2>' +
                '<p class="modal-subtitulo" id="modal-reserva-espaco"></p>' +
                '<div class="modal-erros" id="modal-reserva-erros"><ul></ul></div>' +
                '<form id="form-reserva">' +
                    '<div class="modal-campo">' +
                        '<label for="reserva-data">Data</label>' +
                        '<input type="date" id="reserva-data" required>' +
                    '</div>' +
                    '<div class="modal-linha-dupla">' +
                        '<div class="modal-campo">' +
                            '<label for="reserva-inicio">Horário de início</label>' +
                            '<input type="time" id="reserva-inicio" required>' +
                        '</div>' +
                        '<div class="modal-campo">' +
                            '<label for="reserva-fim">Horário de término</label>' +
                            '<input type="time" id="reserva-fim" required>' +
                        '</div>' +
                    '</div>' +
                    '<div class="modal-campo">' +
                        '<label for="reserva-quantidade">Quantidade prevista de participantes</label>' +
                        '<input type="number" id="reserva-quantidade" min="1" required>' +
                    '</div>' +
                    '<div class="modal-campo">' +
                        '<label for="reserva-finalidade">Finalidade</label>' +
                        '<textarea id="reserva-finalidade" placeholder="Ex: treino de futebol, aniversário infantil..." required></textarea>' +
                    '</div>' +
                    '<div class="modal-acoes">' +
                        '<button type="button" class="modal-btn modal-btn-cancelar" id="reserva-cancelar-btn">Cancelar</button>' +
                        '<button type="submit" class="modal-btn modal-btn-confirmar" id="reserva-confirmar-btn">Solicitar reserva</button>' +
                    '</div>' +
                '</form>' +
            '</div>';

        document.body.appendChild(overlay);

        overlay.addEventListener("click", function (evento) {
            if (evento.target === overlay) {
                fecharModalReserva();
            }
        });

        overlay.querySelector(".modal-fechar").addEventListener("click", fecharModalReserva);
        overlay.querySelector("#reserva-cancelar-btn").addEventListener("click", fecharModalReserva);
        overlay.querySelector("#form-reserva").addEventListener("submit", enviarReserva);

        modalReserva = overlay;
        return overlay;
    }

    let espacoSelecionado = null;

    function abrirModalReserva(espaco) {

        espacoSelecionado = espaco;

        const overlay = garantirModalReserva();
        const nomeTipo = espaco.tipoEspaco ? espaco.tipoEspaco.nome + " · " : "";

        overlay.querySelector("#modal-reserva-espaco").textContent =
            nomeTipo + espaco.nome +
            (espaco.capacidade ? " · capacidade para " + espaco.capacidade + " pessoas" : "");

        overlay.querySelector("#form-reserva").reset();
        overlay.querySelector("#reserva-data").min = dataMinimaReserva();

        if (espaco.capacidade) {
            overlay.querySelector("#reserva-quantidade").max = espaco.capacidade;
        }

        exibirErrosModalReserva([]);

        overlay.classList.add("aberto");
    }

    function fecharModalReserva() {
        if (modalReserva) {
            modalReserva.classList.remove("aberto");
        }
    }

    function exibirErrosModalReserva(erros) {

        const container = modalReserva.querySelector("#modal-reserva-erros");
        const lista = container.querySelector("ul");

        lista.innerHTML = "";

        if (!erros || erros.length === 0) {
            container.classList.remove("visivel");
            return;
        }

        erros.forEach(function (erro) {
            const item = document.createElement("li");
            item.textContent = erro;
            lista.appendChild(item);
        });

        container.classList.add("visivel");
    }

    async function enviarReserva(evento) {

        evento.preventDefault();

        const idSolicitante = obterIdUsuarioLogado();

        if (!idSolicitante) {
            exibirErrosModalReserva(["Sua sessão expirou. Faça login novamente."]);
            return;
        }

        const dados = {
            idSolicitante: idSolicitante,
            idEspaco: espacoSelecionado.idEspaco,
            dtUso: document.getElementById("reserva-data").value,
            horaInicio: document.getElementById("reserva-inicio").value,
            horaFim: document.getElementById("reserva-fim").value,
            qtdPessoas: Number(document.getElementById("reserva-quantidade").value),
            finalidade: document.getElementById("reserva-finalidade").value
        };

        const erros = validarDadosReserva(dados, espacoSelecionado.capacidade);

        if (erros.length > 0) {
            exibirErrosModalReserva(erros);
            return;
        }

        const botao = document.getElementById("reserva-confirmar-btn");
        botao.disabled = true;
        botao.textContent = "Enviando...";

        try {

            await apiSolicitarReserva(dados);

            notificar("Solicitação de reserva enviada com sucesso! Aguarde a análise.", "sucesso");
            fecharModalReserva();

        } catch (erro) {

            exibirErrosModalReserva([erro.message]);

        } finally {
            botao.disabled = false;
            botao.textContent = "Solicitar reserva";
        }
    }

})();
