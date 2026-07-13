/* ==========================================================
   reservas.js
   Listagem das reservas, com detalhes e cancelamento.
   Usado em reservaLogado.html (reservas do próprio solicitante,
   GET /reservas/solicitante/{id}) e em reservaAdmin.html (todas
   as reservas do sistema, GET /reservas).
========================================================== */

(function () {

    const listaReservas = document.getElementById("lista-reservas");

    if (!listaReservas) {
        return;
    }

    const semReservas = document.getElementById("sem-reservas");
    const filtroBotoes = document.querySelectorAll(".filtro-btn");

    const admin = ehAdministrador();
    let reservas = [];

    async function carregarReservas() {

        listaReservas.innerHTML = '<p class="espacos-nota">Carregando reservas...</p>';

        try {
            const idUsuario = obterIdUsuarioLogado();
            const dados = admin
                ? await apiListarReservas()
                : await apiListarReservasPorSolicitante(idUsuario);

            reservas = Array.isArray(dados) ? dados : [];
            aplicarFiltro(filtroAtivo());

        } catch (erro) {
            listaReservas.innerHTML = "";
            notificar(erro.message, "erro");

            if (semReservas) {
                semReservas.style.display = "block";
            }
        }
    }

    function acaoParaStatus(reserva) {

        let html = '<button type="button" class="btn-avaliar btn-detalhes" data-reserva-id="' + reserva.idReserva + '">Detalhes</button>';

        const podeCancelar = !admin &&
            (reserva.status === "DOCUMENTOS_PENDENTES" ||
             reserva.status === "EM_ANALISE" ||
             reserva.status === "APROVADA" ||
             reserva.status === "AGUARDANDO_TERMO" ||
             reserva.status === "CONFIRMADA");

        if (podeCancelar) {
            html += '<button type="button" class="btn-cancelar" data-reserva-id="' + reserva.idReserva + '">Cancelar</button>';
        }

        return html;
    }

    function criarCardReserva(reserva) {

        const card = document.createElement("article");
        card.className = "espaco-card";
        card.dataset.reservaId = reserva.idReserva;
        card.dataset.status = reserva.status;

        card.innerHTML =
            '<div class="espaco-imagem">' + ESPACO_ICONE_PADRAO + '</div>' +
            '<div class="espaco-info">' +
                (admin && reserva.nomeSolicitante ? '<span class="espaco-categoria">' + escaparHtml(reserva.nomeSolicitante) + '</span>' : '') +
                '<h3>' + escaparHtml(reserva.nomeEspaco || "") + '</h3>' +
                '<div class="reserva-data">' +
                    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="5" width="18" height="16" rx="2"/><line x1="3" y1="10" x2="21" y2="10"/><line x1="8" y1="3" x2="8" y2="7"/><line x1="16" y1="3" x2="16" y2="7"/></svg>' +
                    '<span>' + formatarDataBr(reserva.dtUso) + ' · ' + formatarHora(reserva.horaInicio) + ' às ' + formatarHora(reserva.horaFim) + '</span>' +
                '</div>' +
                '<div class="espaco-rodape">' +
                    '<span class="status ' + (RESERVA_STATUS_CLASSES[reserva.status] || "") + '">' + (RESERVA_STATUS_LABELS[reserva.status] || reserva.status) + '</span>' +
                    '<span class="reserva-acoes">' + acaoParaStatus(reserva) + '</span>' +
                '</div>' +
            '</div>';

        const botaoCancelar = card.querySelector(".btn-cancelar");

        if (botaoCancelar) {
            botaoCancelar.addEventListener("click", function (evento) {
                evento.stopPropagation();
                confirmarCancelamento(reserva);
            });
        }

        card.querySelector(".btn-detalhes").addEventListener("click", function (evento) {
            evento.stopPropagation();
            abrirModalDetalhes(reserva);
        });

        return card;
    }

    function renderizarReservas(lista) {

        listaReservas.innerHTML = "";

        if (!lista || lista.length === 0) {
            if (semReservas) {
                semReservas.style.display = "block";
            }
            return;
        }

        if (semReservas) {
            semReservas.style.display = "none";
        }

        lista.forEach(function (reserva) {
            listaReservas.appendChild(criarCardReserva(reserva));
        });
    }

    function filtroAtivo() {
        const botaoAtivo = document.querySelector(".filtro-btn.active");
        return botaoAtivo ? botaoAtivo.dataset.status : "todas";
    }

    function aplicarFiltro(status) {

        filtroBotoes.forEach(function (botao) {
            botao.classList.toggle("active", botao.dataset.status === status);
        });

        const filtradas = status === "todas"
            ? reservas
            : reservas.filter(function (r) { return r.status === status; });

        renderizarReservas(filtradas);
    }

    filtroBotoes.forEach(function (botao) {
        botao.addEventListener("click", function () {
            aplicarFiltro(botao.dataset.status);
        });
    });

    /* ---------- Cancelamento (preserva histórico via PATCH /cancelar) ---------- */

    let modalConfirmacao = null;
    let reservaParaCancelar = null;

    function garantirModalConfirmacao() {

        if (modalConfirmacao) {
            return modalConfirmacao;
        }

        const overlay = document.createElement("div");
        overlay.className = "modal-overlay";
        overlay.id = "modal-cancelar";

        overlay.innerHTML =
            '<div class="modal-caixa">' +
                '<button type="button" class="modal-fechar" aria-label="Fechar">&times;</button>' +
                '<h2>Cancelar reserva</h2>' +
                '<p class="modal-subtitulo" id="modal-cancelar-texto"></p>' +
                '<div class="modal-acoes">' +
                    '<button type="button" class="modal-btn modal-btn-cancelar" id="cancelar-manter">Manter reserva</button>' +
                    '<button type="button" class="modal-btn modal-btn-perigo" id="cancelar-confirmar">Cancelar reserva</button>' +
                '</div>' +
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

        overlay.querySelector("#cancelar-manter").addEventListener("click", function () {
            overlay.classList.remove("aberto");
        });

        overlay.querySelector("#cancelar-confirmar").addEventListener("click", efetivarCancelamento);

        modalConfirmacao = overlay;
        return overlay;
    }

    function confirmarCancelamento(reserva) {

        reservaParaCancelar = reserva;

        const overlay = garantirModalConfirmacao();
        overlay.querySelector("#modal-cancelar-texto").textContent =
            "Tem certeza que deseja cancelar a reserva de " + reserva.nomeEspaco + " em " +
            formatarDataBr(reserva.dtUso) + "?";

        overlay.classList.add("aberto");
    }

    async function efetivarCancelamento() {

        if (!reservaParaCancelar) {
            return;
        }

        const botao = document.getElementById("cancelar-confirmar");
        botao.disabled = true;
        botao.textContent = "Cancelando...";

        try {

            const idSolicitante = obterIdUsuarioLogado();
            await apiCancelarReserva(reservaParaCancelar.idReserva, idSolicitante, null);

            reservas = reservas.map(function (r) {
                return r.idReserva === reservaParaCancelar.idReserva ? Object.assign({}, r, { status: "CANCELADA" }) : r;
            });

            aplicarFiltro(filtroAtivo());
            notificar("Reserva cancelada com sucesso.", "sucesso");
            modalConfirmacao.classList.remove("aberto");

        } catch (erro) {
            notificar(erro.message, "erro");

        } finally {
            botao.disabled = false;
            botao.textContent = "Cancelar reserva";
            reservaParaCancelar = null;
        }
    }

    /* ---------- Detalhes ---------- */

    let modalDetalhes = null;

    function garantirModalDetalhes() {

        if (modalDetalhes) {
            return modalDetalhes;
        }

        const overlay = document.createElement("div");
        overlay.className = "modal-overlay";
        overlay.id = "modal-detalhes";

        overlay.innerHTML =
            '<div class="modal-caixa">' +
                '<button type="button" class="modal-fechar" aria-label="Fechar">&times;</button>' +
                '<h2 id="detalhes-titulo"></h2>' +
                '<div class="modal-detalhes-lista" id="detalhes-lista"></div>' +
                '<div class="modal-acoes">' +
                    '<button type="button" class="modal-btn modal-btn-cancelar" id="detalhes-fechar-btn">Fechar</button>' +
                '</div>' +
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

        overlay.querySelector("#detalhes-fechar-btn").addEventListener("click", function () {
            overlay.classList.remove("aberto");
        });

        modalDetalhes = overlay;
        return overlay;
    }

    function itemDetalhe(rotulo, valor) {
        return '<div class="modal-detalhe-item"><span class="rotulo">' + escaparHtml(rotulo) + '</span><span class="valor">' + escaparHtml(valor) + '</span></div>';
    }

    function abrirModalDetalhes(reserva) {

        const overlay = garantirModalDetalhes();

        overlay.querySelector("#detalhes-titulo").textContent = reserva.nomeEspaco;

        let html = "";

        if (admin && reserva.nomeSolicitante) {
            html += itemDetalhe("Solicitante", reserva.nomeSolicitante);
        }

        html += itemDetalhe("Status", RESERVA_STATUS_LABELS[reserva.status] || reserva.status);
        html += itemDetalhe("Data de uso", formatarDataBr(reserva.dtUso));
        html += itemDetalhe("Horário", formatarHora(reserva.horaInicio) + " às " + formatarHora(reserva.horaFim));

        if (reserva.qtdPessoas) {
            html += itemDetalhe("Participantes previstos", reserva.qtdPessoas);
        }

        if (reserva.finalidade) {
            html += itemDetalhe("Finalidade", reserva.finalidade);
        }

        if (reserva.dtSolicitacao) {
            html += itemDetalhe("Data da solicitação", formatarDataBr(String(reserva.dtSolicitacao).substring(0, 10)));
        }

        overlay.querySelector("#detalhes-lista").innerHTML = html;

        overlay.classList.add("aberto");
    }

    carregarReservas();

})();
