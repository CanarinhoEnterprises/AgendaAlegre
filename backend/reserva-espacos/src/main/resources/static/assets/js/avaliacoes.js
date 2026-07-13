async function carregarAvaliacoes() {

    const idSolicitante = localStorage.getItem("idUsuario");

    if (!idSolicitante) {
        alert("Usuário não encontrado.");
        return;
    }

    try {

        const resposta = await fetch(`/avaliacoes/solicitante/${idSolicitante}`, {
            headers: {
                "Authorization": "Bearer " + pegarToken()
            }
        });

        if (!resposta.ok) {
            throw new Error("Erro ao carregar avaliações.");
        }

        const avaliacoes = await resposta.json();

        const lista = document.getElementById("lista-avaliacoes");
        const semAvaliacoes = document.getElementById("sem-avaliacoes");

        lista.innerHTML = "";

        if (avaliacoes.length === 0) {

    semAvaliacoes.style.display = "block";

} else {

    semAvaliacoes.style.display = "none";

    avaliacoes.forEach(avaliacao => {

        const estrelas = "★".repeat(avaliacao.nota) + "☆".repeat(5 - avaliacao.nota);

        const card = document.createElement("article");
        card.className = "card-espaco";
        card.dataset.nota = avaliacao.nota;

        card.innerHTML = `
            ...
        `;

        lista.appendChild(card);

    });

}

        semAvaliacoes.style.display = "none";

        avaliacoes.forEach(avaliacao => {

            const estrelas = "★".repeat(avaliacao.nota) + "☆".repeat(5 - avaliacao.nota);

            const card = document.createElement("article");
            card.className = "card-espaco";
            card.dataset.nota = avaliacao.nota;

            card.innerHTML = `

                <img
                    src="${avaliacao.urlCapa}"
                    alt="${avaliacao.espacoNome}"
                    class="espaco-img">

                <div class="espaco-info">

                    <h3>${avaliacao.espacoNome}</h3>

                    <p><strong>Categoria:</strong> ${avaliacao.categoria}</p>

                    <p><strong>Localização:</strong> ${avaliacao.localizacao}</p>

                    <p><strong>Usuário:</strong> ${avaliacao.nomeUsuario}</p>

                    <p><strong>Data:</strong> ${formatarData(avaliacao.dataAvaliacao)}</p>

                    <div class="estrelas">
                        ${estrelas}
                    </div>

                    <p class="comentario">
                        "${avaliacao.comentario}"
                    </p>

                </div>

            `;

            lista.appendChild(card);

        });

    } catch (erro) {

        console.error(erro);

        alert("Erro ao carregar avaliações.");

    }

}

function formatarData(data) {

    return new Date(data).toLocaleDateString("pt-BR");

}

function configurarFiltros() {

    const botoes = document.querySelectorAll(".filtro-btn");

    botoes.forEach(botao => {

        botao.addEventListener("click", () => {

            botoes.forEach(btn => btn.classList.remove("active"));

            botao.classList.add("active");

            const nota = botao.dataset.nota;

            const cards = document.querySelectorAll("#lista-avaliacoes .card-espaco");

            cards.forEach(card => {

                if (nota === "todas") {

                    card.style.display = "block";

                } else {

                    card.style.display =
                        card.dataset.nota === nota ? "block" : "none";

                }

            });

        });

    });

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
        const avaliacoes = await respostaAvaliacoes.json();

        const hoje = new Date();

        const pendentes = reservas.filter(reserva => {

            const dataUso = new Date(reserva.dtUso);

            return reserva.status === "CONFIRMADA"
                && dataUso <= hoje
                && !avaliacoes.some(av =>
                    Number(av.reservaId) === reserva.idReserva
                );

        });


        const semAvaliacoes = document.getElementById("sem-avaliacoes");

        if (pendentes.length > 0) {
            semAvaliacoes.style.display = "none";
        }

        const lista = document.getElementById("lista-pendentes");
        const semPendentes = document.getElementById("sem-pendentes");

        lista.innerHTML = "";

        pendentes.forEach(reserva => {

            const card = document.createElement("article");

            card.className = "card-espaco pendente";

            card.innerHTML = `

                <img
                    src="${reserva.urlCapa}"
                    class="espaco-img">

                <div class="espaco-info">

                    <h3>${reserva.nomeEspaco}</h3>

                    <p><strong>Categoria:</strong> ${reserva.categoria}</p>

                    <p><strong>Localização:</strong> ${reserva.localizacao}</p>

                    <p><strong>Status:</strong> Avaliação pendente</p>

                    <div class="estrelas">

                        <span data-nota="1">☆</span>
                        <span data-nota="2">☆</span>
                        <span data-nota="3">☆</span>
                        <span data-nota="4">☆</span>
                        <span data-nota="5">☆</span>

                    </div>

                    <textarea
                        placeholder="Escreva um comentário..."
                        class="comentario-avaliacao">
                    </textarea>

                    <button class="btn-avaliar">
                        Enviar avaliação
                    </button>

                </div>

            `;

            lista.appendChild(card);

            const estrelas = card.querySelectorAll(".estrelas span");

estrelas.forEach(estrela => {

    estrela.addEventListener("click", () => {

        const nota = Number(estrela.dataset.nota);

        card.dataset.nota = nota;

        estrelas.forEach(s => {

            s.textContent =
                Number(s.dataset.nota) <= nota ? "★" : "☆";

        });

    });

});

const botao = card.querySelector(".btn-avaliar");

botao.addEventListener("click", async () => {

    const comentario = card.querySelector(".comentario-avaliacao").value.trim();

    const nota = Number(card.dataset.nota);

    if (!nota) {
        alert("Selecione uma nota.");
        return;
    }

    try {

        const resposta = await fetch("/avaliacoes", {

            method: "POST",

            headers: {

                "Content-Type": "application/json",

                "Authorization": "Bearer " + pegarToken()

            },

            body: JSON.stringify({

                idReserva: reserva.idReserva,
                nota: nota,
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

    configurarFiltros();

    carregarAvaliacoes();

    carregarAvaliacoesPendentes();

});