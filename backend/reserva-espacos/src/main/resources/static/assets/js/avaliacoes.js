let avaliacoes = [];

const listaAvaliacoes = document.getElementById("lista-avaliacoes");
const semAvaliacoes = document.getElementById("sem-avaliacoes");
const filtroBotoes = document.querySelectorAll(".filtro-btn");

async function carregarAvaliacoes() {

    const idSolicitante = pegarIdUsuario();

    if (!idSolicitante) {
        return;
    }

    try {

        const resposta = await fetch(`/avaliacoes/solicitante/${idSolicitante}`, {
            headers: {
                Authorization: "Bearer " + pegarToken()
            }
        });

        if (!resposta.ok) {
            throw new Error();
        }

        avaliacoes = await resposta.json();

        aplicarFiltro("todas");

    } catch (erro) {

        console.error(erro);

        alert("Erro ao carregar avaliações.");

    }

}

function renderizarAvaliacoes(lista) {

    listaAvaliacoes.innerHTML = "";

    if (!lista || lista.length === 0) {

        semAvaliacoes.style.display = "block";
        return;

    }

    semAvaliacoes.style.display = "none";

    lista.forEach(av => {

        listaAvaliacoes.appendChild(criarCardAvaliacao(av));

    });

}

function criarCardAvaliacao(av) {

    const card = document.createElement("article");

    card.className = "espaco-card";

    card.dataset.nota = av.nota;

    card.innerHTML = `

        <div class="espaco-imagem">

            <img
                src="${av.urlCapa || '../../assets/img/espaco-placeholder.jpg'}"
                alt="${av.espacoNome}"
                onerror="this.src='../../assets/img/espaco-placeholder.jpg'">

        </div>

        <div class="espaco-info">

            <h3>${av.espacoNome}</h3>

            <p><strong>Usuário:</strong> ${av.nomeUsuario}</p>

            <div class="estrelas-display">
                Nota: ${av.nota}/5
            </div>

            <p>${av.comentario || "Sem comentário."}</p>

            <div class="espaco-rodape">
                <span>${formatarData(av.dataAvaliacao)}</span>
            </div>

        </div>

    `;

    return card;

}

function aplicarFiltro(nota) {

    filtroBotoes.forEach(btn =>
        btn.classList.toggle("active", btn.dataset.nota === nota)
    );

    const listaFiltrada = nota === "todas"
        ? avaliacoes
        : avaliacoes.filter(av => String(av.nota) === nota);

    renderizarAvaliacoes(listaFiltrada);

}

filtroBotoes.forEach(btn => {

    btn.addEventListener("click", () => {

        aplicarFiltro(btn.dataset.nota);

    });

});

function formatarData(data) {

    if (!data) return "";

    return new Date(data).toLocaleDateString("pt-BR");

}

async function carregarAvaliacoesPendentes() {

    const idSolicitante = pegarIdUsuario();

    try {

        const [respostaReservas, respostaAvaliacoes] = await Promise.all([

            fetch(`/reservas/solicitante/${idSolicitante}`, {
                headers: {
                    Authorization: "Bearer " + pegarToken()
                }
            }),

            fetch(`/avaliacoes/solicitante/${idSolicitante}`, {
                headers: {
                    Authorization: "Bearer " + pegarToken()
                }
            })

        ]);

        const reservas = await respostaReservas.json();

        const avaliacoesUsuario = await respostaAvaliacoes.json();

        const hoje = new Date();

        const pendentes = reservas.filter(reserva => {

            const dataUso = new Date(reserva.dtUso);

            return reserva.status === "CONFIRMADA"
                && dataUso <= hoje
                && !avaliacoesUsuario.some(av => Number(av.reservaId) === reserva.idReserva);

        });

        const lista = document.getElementById("lista-pendentes");

        const semPendentes = document.getElementById("sem-pendentes");

        lista.innerHTML = "";

        if (pendentes.length === 0) {

            semPendentes.style.display = "block";
            return;

        }

        semPendentes.style.display = "none";

        pendentes.forEach(reserva => {

            const card = document.createElement("article");

            card.className = "espaco-card";

            card.innerHTML = `

                <div class="espaco-imagem">

                    <img
                        src="${reserva.urlCapa || '../../assets/img/espaco-placeholder.jpg'}"
                        alt="${reserva.nomeEspaco}"
                        onerror="this.src='../../assets/img/espaco-placeholder.jpg'">

                </div>

                <div class="espaco-info">

                    <h3>${reserva.nomeEspaco}</h3>

                    <span class="status-pendente">
                        Avaliação pendente
                    </span>

                    <button class="btn-abrir-avaliacao">
                        Avaliar agora
                    </button>

                    <div class="form-avaliacao">

                        <label class="titulo-avaliacao">

                            Como foi sua experiência?

                        </label>

                        <div class="estrelas-input">

                            <button class="estrela-btn" data-nota="1">
                                <i data-lucide="star" class="icone-estrela"></i>
                            </button>

                            <button class="estrela-btn" data-nota="2">
                                <i data-lucide="star" class="icone-estrela"></i>
                            </button>

                            <button class="estrela-btn" data-nota="3">
                                <i data-lucide="star" class="icone-estrela"></i>
                            </button>

                            <button class="estrela-btn" data-nota="4">
                                <i data-lucide="star" class="icone-estrela"></i>
                            </button>

                            <button class="estrela-btn" data-nota="5">
                                <i data-lucide="star" class="icone-estrela"></i>
                            </button>

                        </div>

                        <textarea class="comentario-avaliacao" placeholder="Escreva um comentário..."></textarea>

                        <div class="form-avaliacao-acoes">

                            <button
                                type="button"
                                class="btn-texto-secundario btn-cancelar-avaliacao">

                                Cancelar

                            </button>

                            <button
                                type="button"
                                class="btn-avaliar">

                                Enviar avaliação

                            </button>

                        </div>

                    </div>

                </div>

            `;

            lista.appendChild(card);

            lucide.createIcons({
                attrs: {
                    "stroke-width": 2
                }
            });

            const botaoAbrir = card.querySelector(".btn-abrir-avaliacao");

            const formulario = card.querySelector(".form-avaliacao");

            const botaoCancelar = card.querySelector(".btn-cancelar-avaliacao");

            botaoAbrir.addEventListener("click", () => {

                formulario.classList.add("aberto");

                botaoAbrir.style.display = "none";

            });

            botaoCancelar.addEventListener("click", () => {

                formulario.classList.remove("aberto");

                botaoAbrir.style.display = "block";

            });

            let notaSelecionada = 0;

            const estrelas = card.querySelectorAll(".estrela-btn");

                estrelas.forEach(estrela => {

                    estrela.addEventListener("click", () => {

                        notaSelecionada = Number(estrela.dataset.nota);

                        estrelas.forEach(s => {

                            const icone = s.querySelector(".icone-estrela");

                            icone.classList.toggle(
                                "preenchida",
                                Number(s.dataset.nota) <= notaSelecionada
                            );

                        });

                    });

                });

            card.querySelector(".btn-avaliar").addEventListener("click", async () => {

                const comentario = card.querySelector(".comentario-avaliacao").value.trim();

                if (!notaSelecionada) {

                    alert("Selecione uma nota.");
                    return;

                }

                try {

                    const resposta = await fetch("/avaliacoes", {

                        method: "POST",

                        headers: {

                            "Content-Type": "application/json",

                            Authorization: "Bearer " + pegarToken()

                        },

                        body: JSON.stringify({

                            idReserva: reserva.idReserva,
                            nota: notaSelecionada,
                            comentario: comentario,
                            dtAvaliacao: new Date().toISOString().split("T")[0]

                        })

                    });

                    if (!resposta.ok) {

                        throw new Error();

                    }

                    alert("Avaliação enviada com sucesso!");

                    await carregarAvaliacoes();

                    await carregarAvaliacoesPendentes();

                } catch (erro) {

                    console.error(erro);

                    alert("Erro ao enviar avaliação.");

                }

            });

        });

    } catch (erro) {

        console.error(erro);

    }

}

document.addEventListener("DOMContentLoaded", () => {

    carregarAvaliacoes();

    carregarAvaliacoesPendentes();

});