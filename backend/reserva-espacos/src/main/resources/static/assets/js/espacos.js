const API_URL = "http://localhost:8081";
const GRID_ID = "espacosGrid";
const NOTA_ID = "espacosNota";
const CATEGORIAS_VALIDAS = ["quadras", "parques", "saloes", "auditorios"];

const ICONES = {
    quadras: '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.4" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="5" width="18" height="14" rx="2"/><line x1="12" y1="5" x2="12" y2="19"/><circle cx="12" cy="12" r="3"/></svg>',
    parques: '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.4" stroke-linecap="round" stroke-linejoin="round"><path d="M12 3 7 11h3l-4 6h12l-4-6h3z"/><line x1="12" y1="17" x2="12" y2="21"/></svg>',
    saloes: '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.4" stroke-linecap="round" stroke-linejoin="round"><rect x="4" y="10" width="16" height="10" rx="1.5"/><line x1="4" y1="14" x2="20" y2="14"/><line x1="12" y1="10" x2="12" y2="20"/><path d="M12 10c-3 0-4-3 0-4 4 1 3 4 0 4Z"/></svg>',
    auditorios: '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.4" stroke-linecap="round" stroke-linejoin="round"><path d="M4 9 12 4l8 5"/><line x1="4" y1="9" x2="20" y2="9"/><line x1="6" y1="9" x2="6" y2="18"/><line x1="10" y1="9" x2="10" y2="18"/><line x1="14" y1="9" x2="14" y2="18"/><line x1="18" y1="9" x2="18" y2="18"/><line x1="4" y1="18" x2="20" y2="18"/><line x1="4" y1="21" x2="20" y2="21"/></svg>'
};

const ROTULOS = {
    quadras: "Quadra esportiva",
    parques: "Parque",
    saloes: "Salão de festas",
    auditorios: "Auditório"
};

function normalizarTexto(texto) {
    return (texto || "")
        .toLowerCase()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "")
        .trim();
}

function categorizarEspaco(espaco) {
    const nomeTipo = normalizarTexto(espaco?.tipoEspaco?.nome);

    if (nomeTipo.includes("quadra")) {
        return "quadras";
    }
    if (nomeTipo.includes("parque") || nomeTipo.includes("praca")) {
        return "parques";
    }
    if (nomeTipo.includes("salao")) {
        return "saloes";
    }
    if (nomeTipo.includes("auditorio") || nomeTipo.includes("centro cultural")) {
        return "auditorios";
    }

    return "parques";
}

function montarLocalizacao(espaco) {
    const bairro = espaco?.endereco?.bairro || "Bairro não informado";
    const cidade = espaco?.endereco?.cidade?.nome || "Alegre";
    const uf = espaco?.endereco?.cidade?.uf || "ES";

    return `${bairro}, ${cidade} - ${uf}`;
}

function montarUrlImagem(espaco) {
    if (espaco?.urlCapa) {
        return espaco.urlCapa;
    }

    return null;
}

function montarBotaoReserva(espaco, paginaLogada) {
    if (espaco.status !== "ATIVO") {
        return '<span class="btn-reservar btn-desabilitado">Reservar</span>';
    }

    const href = paginaLogada ? `../reserva.html?id=${espaco.idEspaco}` : `login.html`;
    return `<a href="${href}" class="btn-reservar">Reservar</a>`;
}

function montarCard(espaco, paginaLogada) {
    const categoria = categorizarEspaco(espaco);
    const statusDisponivel = espaco.status === "ATIVO";
    const localizacao = montarLocalizacao(espaco);
    const urlImagem = montarUrlImagem(espaco);
    const imagemConteudo = urlImagem
        ? `<img src="${urlImagem}" alt="${espaco.nome}" class="espaco-card-img">`
        : `${ICONES[categoria]}<span>Imagem do espaço</span>`;

    return `
        <article class="espaco-card" data-categoria="${categoria}">
            <div class="espaco-imagem ${urlImagem ? "espaco-imagem-com-foto" : ""}">
                ${imagemConteudo}
            </div>
            <div class="espaco-info">
                <span class="espaco-categoria">${ROTULOS[categoria]}</span>
                <h3>${espaco.nome}</h3>
                <div class="espaco-local">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 21C12 21 19 14.5 19 9.5A7 7 0 0 0 5 9.5C5 14.5 12 21 12 21Z"/><circle cx="12" cy="9.5" r="2.3"/></svg>
                    <span>${localizacao}</span>
                </div>
                <div class="espaco-rodape">
                    <span class="status ${statusDisponivel ? "status-disponivel" : "status-indisponivel"}">${statusDisponivel ? "Disponível" : "Indisponível"}</span>
                    ${montarBotaoReserva(espaco, paginaLogada)}
                </div>
            </div>
        </article>
    `;
}

function configurarFiltros() {
    const filtroBotoes = document.querySelectorAll(".filtro-btn");
    const aplicarFiltro = function (categoria) {
        filtroBotoes.forEach(function (botao) {
            botao.classList.toggle("active", botao.dataset.categoria === categoria);
        });

        document.querySelectorAll(".espaco-card").forEach(function (card) {
            const mostrar = categoria === "todos" || card.dataset.categoria === categoria;
            card.style.display = mostrar ? "" : "none";
        });
    };

    filtroBotoes.forEach(function (botao) {
        botao.addEventListener("click", function () {
            aplicarFiltro(botao.dataset.categoria);
        });
    });

    const categoriaUrl = new URLSearchParams(window.location.search).get("categoria");
    aplicarFiltro(CATEGORIAS_VALIDAS.includes(categoriaUrl) ? categoriaUrl : "todos");
}

async function carregarEspacos() {
    const grid = document.getElementById(GRID_ID);
    const nota = document.getElementById(NOTA_ID);

    if (!grid) {
        return;
    }

    grid.innerHTML = "";
    grid.setAttribute("aria-busy", "true");

    try {
        const resposta = await fetch(`${API_URL}/espacos`);

        if (!resposta.ok) {
            throw new Error("Falha ao carregar espaços");
        }

        const espacos = await resposta.json();
        const paginaLogada = window.location.pathname.includes("/usuario/");

        if (!Array.isArray(espacos) || espacos.length === 0) {
            grid.innerHTML = '<p class="espacos-nota">Nenhum espaço cadastrado no momento.</p>';
            if (nota) {
                nota.textContent = "Nenhum espaço cadastrado no momento.";
            }
            return;
        }

        grid.innerHTML = espacos.map(function (espaco) {
            return montarCard(espaco, paginaLogada);
        }).join("");

        if (nota) {
            nota.textContent = `${espacos.length} espaço(s) carregado(s) do banco de dados.`;
        }

        configurarFiltros();
    } catch (erro) {
        grid.innerHTML = '<p class="espacos-nota">Não foi possível carregar os espaços no momento.</p>';
        if (nota) {
            nota.textContent = "Erro ao carregar os espaços.";
        }
    } finally {
        grid.removeAttribute("aria-busy");
    }
}

document.addEventListener("DOMContentLoaded", carregarEspacos);
