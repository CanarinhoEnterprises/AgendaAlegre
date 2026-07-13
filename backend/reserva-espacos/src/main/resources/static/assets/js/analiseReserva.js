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
        card.className = "espaco-card";
        card.dataset.reservaId = reserva.idReserva;

        card.innerHTML =
            '<div class="espaco-imagem">' + ESPACO_ICONE_PADRAO + '</div>' +
            '<div class="espaco-info">' +
                '<h3>' + escaparHtml(reserva.nomeEspaco || "") + '</h3>' +
                '<div class="reserva-data">' +
                    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="5" width="18" height="16" rx="2"/><line x1="3" y1="10" x2="21" y2="10"/><line x1="8" y1="3" x2="8" y2="7"/><line x1="16" y1="3" x2="16" y2="7"/></svg>' +
                    '<span>' + formatarDataBr(reserva.dtUso) + ' · ' + formatarHora(reserva.horaInicio) + ' às ' + formatarHora(reserva.horaFim) + '</span>' +
                '</div>' +
                '<div class="analise-info">' +
                    (reserva.nomeSolicitante ? '<p><strong>Solicitante:</strong> ' + escaparHtml(reserva.nomeSolicitante) + '</p>' : '') +
                    (reserva.qtdPessoas ? '<p><strong>Participantes previstos:</strong> ' + escaparHtml(reserva.qtdPessoas) + '</p>' : '') +
                    (reserva.finalidade ? '<p><strong>Finalidade:</strong> ' + escaparHtml(reserva.finalidade) + '</p>' : '') +
                '</div>' +
                '<div class="espaco-rodape analise-rodape">' +
                    '<button type="button" class="modal-btn modal-btn-confirmar btn-aprovar" data-reserva-id="' + reserva.idReserva + '">Aprovar</button>' +
                    '<button type="button" class="modal-btn modal-btn-perigo btn-rejeitar" data-reserva-id="' + reserva.idReserva + '">Rejeitar</button>' +
                '</div>' +
            '</div>';

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
