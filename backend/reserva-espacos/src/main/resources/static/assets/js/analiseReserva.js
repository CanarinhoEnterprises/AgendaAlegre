/* ==========================================================
   analiseReserva.js
   Tela do administrador para análise de solicitações de
   reserva (aprovação / rejeição). Usa GET /reservas/status/EM_ANALISE
   e POST /analises-reserva.
========================================================== */

(function () {

    const lista = document.getElementById("lista-analise");

    if (!lista) {
        return;
    }

    const semPendentes = document.getElementById("sem-pendentes");
    const idAdministrador = obterIdUsuarioLogado();

    let pendentes = [];

    async function carregarPendentes() {

        lista.innerHTML = '<p class="espacos-nota">Carregando solicitações...</p>';

        try {
            const dados = await apiListarReservasPorStatus("EM_ANALISE");
            pendentes = Array.isArray(dados) ? dados : [];
            renderizar();

        } catch (erro) {
            lista.innerHTML = "";
            notificar(erro.message, "erro");

            if (semPendentes) {
                semPendentes.style.display = "block";
            }
        }
    }

    function renderizar() {

        lista.innerHTML = "";

        if (pendentes.length === 0) {
            if (semPendentes) {
                semPendentes.style.display = "block";
            }
            return;
        }

        if (semPendentes) {
            semPendentes.style.display = "none";
        }

        pendentes.forEach(function (reserva) {
            lista.appendChild(criarCardAnalise(reserva));
        });
    }

    function criarCardAnalise(reserva) {

        const card = document.createElement("article");
        card.className = "card-reserva"; // Agora bate perfeitamente com o analiseReserva.css
        card.dataset.reservaId = reserva.idReserva;

        card.innerHTML =
            '<div class="card-header">' +
                '<div>' +
                    '<span class="status pendente">Pendente</span>' +
                    '<h2>' + escaparHtml(reserva.nomeEspaco || "") + '</h2>' +
                    '<p class="usuario">Solicitação realizada por <strong>' + escaparHtml(reserva.nomeSolicitante || "Usuário não identificado") + '</strong></p>' +
                '</div>' +
            '</div>' +
            '<div class="detalhes">' +
                '<div class="linha">' +
                    '<div class="item">' +
                        '<span>Data da Reserva</span>' +
                        '<strong>' + formatarDataBr(reserva.dtUso) + '</strong>' +
                    '</div>' +
                    '<div class="item">' +
                        '<span>Horário</span>' +
                        '<strong>' + formatarHora(reserva.horaInicio) + ' às ' + formatarHora(reserva.horaFim) + '</strong>' +
                    '</div>' +
                '</div>' +
                '<div class="linha">' +
                    '<div class="item">' +
                        '<span>Finalidade</span>' +
                        '<strong>' + escaparHtml(reserva.finalidade || "Não informada") + '</strong>' +
                    '</div>' +
                '</div>' +
                '<div class="linha">' +
                    '<div class="item">' +
                        '<span>Participantes previstos</span>' +
                        '<strong>' + escaparHtml(reserva.qtdPessoas || "-") + ' pessoas</strong>' +
                    '</div>' +
                '</div>' +
                '<div class="acoes">' +
                    '<button type="button" class="btn-aceitar btn-aprovar" data-reserva-id="' + reserva.idReserva + '">Aceitar Reserva</button>' +
                    '<button type="button" class="btn-recusar btn-rejeitar" data-reserva-id="' + reserva.idReserva + '">Recusar Reserva</button>' +
                '</div>' +
            '</div>';

        // Os botões continuam executando as funções corretas de API
        card.querySelector(".btn-aprovar").addEventListener("click", function () {
            aprovarReserva(reserva);
        });

        card.querySelector(".btn-rejeitar").addEventListener("click", function () {
            abrirModalRejeicao(reserva);
        });

        return card;
    }

    async function aprovarReserva(reserva) {

        const botao = lista.querySelector('.btn-aprovar[data-reserva-id="' + reserva.idReserva + '"]');

        if (botao) {
            botao.disabled = true;
            botao.textContent = "Aprovando...";
        }

        try {

            await apiAprovarReserva(reserva.idReserva, idAdministrador, null);

            pendentes = pendentes.filter(function (r) { return r.idReserva !== reserva.idReserva; });
            renderizar();

            notificar("Reserva aprovada com sucesso.", "sucesso");

        } catch (erro) {
            notificar(erro.message, "erro");

            if (botao) {
                botao.disabled = false;
                botao.textContent = "Aprovar";
            }
        }
    }

    /* ---------- Modal de rejeição (motivo obrigatório) ---------- */

    let modalRejeicao = null;
    let reservaParaRejeitar = null;

    function garantirModalRejeicao() {

        if (modalRejeicao) {
            return modalRejeicao;
        }

        const overlay = document.createElement("div");
        overlay.className = "modal-overlay";
        overlay.id = "modal-rejeicao";

        overlay.innerHTML =
            '<div class="modal-caixa">' +
                '<button type="button" class="modal-fechar" aria-label="Fechar">&times;</button>' +
                '<h2>Rejeitar reserva</h2>' +
                '<p class="modal-subtitulo" id="modal-rejeicao-texto"></p>' +
                '<div class="modal-erros" id="modal-rejeicao-erros"><ul></ul></div>' +
                '<form id="form-rejeicao">' +
                    '<div class="modal-campo">' +
                        '<label for="rejeicao-motivo">Motivo da rejeição</label>' +
                        '<textarea id="rejeicao-motivo" placeholder="Explique o motivo da rejeição..." required></textarea>' +
                    '</div>' +
                    '<div class="modal-acoes">' +
                        '<button type="button" class="modal-btn modal-btn-cancelar" id="rejeicao-cancelar-btn">Cancelar</button>' +
                        '<button type="submit" class="modal-btn modal-btn-perigo" id="rejeicao-confirmar-btn">Confirmar rejeição</button>' +
                    '</div>' +
                '</form>' +
            '</div>';

        document.body.appendChild(overlay);

        overlay.addEventListener("click", function (evento) {
            if (evento.target === overlay) {
                overlay.classList.remove("aberto");
            }
        });

        overlay.querySelector(".modal-fechar").addEventListener("click", function () {
            overlay.classList.remove("aberto");
        });

        overlay.querySelector("#rejeicao-cancelar-btn").addEventListener("click", function () {
            overlay.classList.remove("aberto");
        });

        overlay.querySelector("#form-rejeicao").addEventListener("submit", confirmarRejeicao);

        modalRejeicao = overlay;
        return overlay;
    }

    function abrirModalRejeicao(reserva) {

        reservaParaRejeitar = reserva;

        const overlay = garantirModalRejeicao();

        overlay.querySelector("#modal-rejeicao-texto").textContent =
            "Reserva de " + reserva.nomeEspaco + " em " + formatarDataBr(reserva.dtUso) + ".";

        overlay.querySelector("#form-rejeicao").reset();
        exibirErroRejeicao("");

        overlay.classList.add("aberto");
    }

    function exibirErroRejeicao(mensagem) {

        const container = modalRejeicao.querySelector("#modal-rejeicao-erros");
        const listaErros = container.querySelector("ul");

        listaErros.innerHTML = "";

        if (!mensagem) {
            container.classList.remove("visivel");
            return;
        }

        const item = document.createElement("li");
        item.textContent = mensagem;
        listaErros.appendChild(item);

        container.classList.add("visivel");
    }

    async function confirmarRejeicao(evento) {

        evento.preventDefault();

        const motivo = document.getElementById("rejeicao-motivo").value.trim();

        if (!motivo) {
            exibirErroRejeicao("O motivo da rejeição é obrigatório.");
            return;
        }

        const botao = document.getElementById("rejeicao-confirmar-btn");
        botao.disabled = true;
        botao.textContent = "Enviando...";

        try {

            await apiRejeitarReserva(reservaParaRejeitar.idReserva, idAdministrador, motivo);

            pendentes = pendentes.filter(function (r) { return r.idReserva !== reservaParaRejeitar.idReserva; });
            renderizar();

            notificar("Reserva rejeitada.", "sucesso");
            modalRejeicao.classList.remove("aberto");

        } catch (erro) {
            exibirErroRejeicao(erro.message);

        } finally {
            botao.disabled = false;
            botao.textContent = "Confirmar rejeição";
            reservaParaRejeitar = null;
        }
    }

    carregarPendentes();

})();
