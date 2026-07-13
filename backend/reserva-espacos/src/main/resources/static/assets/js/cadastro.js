async function cadastrarUsuario(){

    const usuario = {
        nome: document.getElementById("nome").value,
        telefone: document.getElementById("telefone").value,
        email: document.getElementById("email").value,
        senha: document.getElementById("senha").value,
        cpfCnpj: document.getElementById("documento").value,
        tipoPessoa: document.getElementById("tipoPessoa").value
    };

    const botao = document.querySelector("#formCadastro button[type=submit]");
    if (botao) {
        botao.disabled = true;
        botao.textContent = "Enviando...";
    }

    try {
        await apiCadastrarUsuario(usuario);

        notificar("Usuário cadastrado com sucesso! Faça login para continuar.", "sucesso");

        setTimeout(function () {
            window.location.href = "login.html";
        }, 1200);

    } catch (erro) {
        notificar(erro.message, "erro");

    } finally {
        if (botao) {
            botao.disabled = false;
            botao.textContent = "Criar Conta";
        }
    }
}

document.getElementById("formCadastro").addEventListener("submit", async function(event){

        event.preventDefault();
        await cadastrarUsuario();
});
