window.addEventListener("DOMContentLoaded", () => {
    const emailSalvo = sessionStorage.getItem("loginEmail");

    if (emailSalvo) {
        document.getElementById("email").value = emailSalvo;
        document.getElementById("senha").focus();

        // Remove para não ficar preenchendo sempre
        sessionStorage.removeItem("loginEmail");
    }
});

document.getElementById("formLogin").addEventListener("submit", fazerLogin);

async function fazerLogin(event){
    event.preventDefault();

    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    const resposta = await fetch("http://localhost:8081/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email,
            senha
        })
    });

    if(!resposta.ok){
        alert("Email ou senha inválidos!");
        return;
    }


    const usuario = await resposta.json();

    localStorage.setItem("idUsuario", usuario.id);

    localStorage.setItem("token", usuario.token);

    if (typeof salvarUsuarioLogado === "function") {
        salvarUsuarioLogado(usuario);
    } else {
        localStorage.setItem("agendaAlegre.usuario", JSON.stringify(usuario));
    }

    if(usuario.tipo === "ADMINISTRADOR"){
        window.location.href = "/pages/admin/espacoAdmin.html";
    }else{
        window.location.href = "/pages/usuario/espacoLogado.html";
    }
}