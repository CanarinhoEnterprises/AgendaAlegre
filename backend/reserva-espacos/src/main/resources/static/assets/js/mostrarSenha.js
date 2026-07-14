document.addEventListener("DOMContentLoaded", () => {

    const campoSenha = document.getElementById("senha");

    const botao = document.querySelector(".toggle-senha");
    let mostrandoSenha = false;

    if(!campoSenha || !botao){
        return;
    }

    botao.addEventListener("click", () => {

        mostrandoSenha = !mostrandoSenha;

        campoSenha.type = mostrandoSenha ? "text" : "password";

        botao.innerHTML = mostrandoSenha
            ? '<i data-lucide="eye-off"></i>'
            : '<i data-lucide="eye"></i>';

        lucide.createIcons();

    });

});