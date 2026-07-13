async function cadastrarUsuario(){

    const usuario = {
        nome: document.getElementById("nome").value,
        telefone: document.getElementById("telefone").value,
        email: document.getElementById("email").value,
        senha: document.getElementById("senha").value,
        cpfCnpj: document.getElementById("documento").value,
        tipoPessoa: document.getElementById("tipoPessoa").value
    };


    const resposta = await fetch("/usuarios", {
        method: "POST",

        headers:{
            "Content-Type":"application/json"
        },

        body: JSON.stringify(usuario)
    });


    if(resposta.ok){
        const Email = document.getElementById("email").value;
        sessionStorage.setItem("loginEmail", Email);

        alert("Usuário cadastrado!");
        window.location.href = "login.html";
    } else {
        const mensagem = await resposta.text();

        alert(mensagem);
    }

}


document.getElementById("formCadastro").addEventListener("submit", async function(event){

        event.preventDefault();
        await cadastrarUsuario();
});