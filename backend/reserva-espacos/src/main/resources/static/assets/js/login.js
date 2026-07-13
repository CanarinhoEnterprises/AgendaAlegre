document.getElementById("formLogin").addEventListener("submit", fazerLogin);

async function fazerLogin(event){
    event.preventDefault();

    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    const botao = event.target.querySelector("button[type=submit]");
    if (botao) {
        botao.disabled = true;
        botao.textContent = "Entrando...";
    }

    try {
        const usuario = await apiLogin(email, senha);

        salvarUsuarioLogado(usuario);

        if (usuario.tipo === "ADMINISTRADOR") {
            window.location.href = "/pages/admin/espacoAdmin.html";
        } else {
            window.location.href = "/pages/usuario/espacoLogado.html";
        }

    } catch (erro) {
        notificar(erro.message, "erro");

    } finally {
        if (botao) {
            botao.disabled = false;
            botao.textContent = "Entrar";
        }
    }
}
