/* ==========================================================
   notificacao.js
   Sistema simples de notificações (toasts) reutilizado em
   todas as páginas do Agenda Alegre.
========================================================== */

function obterContainerNotificacoes() {

    let container = document.getElementById("notificacoes-container");

    if (!container) {
        container = document.createElement("div");
        container.id = "notificacoes-container";
        container.className = "notificacoes-container";
        document.body.appendChild(container);
    }

    return container;
}

/*
    tipo: "sucesso" | "erro" | "info" | "aviso"
*/
function notificar(mensagem, tipo) {

    const tipoFinal = tipo || "info";
    const container = obterContainerNotificacoes();

    const toast = document.createElement("div");
    toast.className = "notificacao notificacao-" + tipoFinal;

    const icones = {
        sucesso: "check-circle",
        erro: "x-circle",
        aviso: "alert-triangle",
        info: "info"
    };

    toast.innerHTML =
        '<i data-lucide="' + (icones[tipoFinal] || "info") + '"></i>' +
        '<span class="notificacao-texto"></span>' +
        '<button type="button" class="notificacao-fechar" aria-label="Fechar">&times;</button>';

    toast.querySelector(".notificacao-texto").textContent = mensagem;

    toast.querySelector(".notificacao-fechar").addEventListener("click", function () {
        removerNotificacao(toast);
    });

    container.appendChild(toast);

    if (window.lucide && typeof lucide.createIcons === "function") {
        lucide.createIcons();
    }

    requestAnimationFrame(function () {
        toast.classList.add("visivel");
    });

    setTimeout(function () {
        removerNotificacao(toast);
    }, 5000);

    return toast;
}

function removerNotificacao(toast) {

    if (!toast || !toast.parentNode) {
        return;
    }

    toast.classList.remove("visivel");
    toast.classList.add("saindo");

    setTimeout(function () {
        if (toast.parentNode) {
            toast.parentNode.removeChild(toast);
        }
    }, 300);
}
