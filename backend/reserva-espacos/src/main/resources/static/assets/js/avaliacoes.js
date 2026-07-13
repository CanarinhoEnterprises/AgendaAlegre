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
            return;

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

document.addEventListener("DOMContentLoaded", () => {

    configurarFiltros();

    carregarAvaliacoes();

});