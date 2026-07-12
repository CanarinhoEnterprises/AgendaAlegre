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

    if(usuario.tipo === "ADMINISTRADOR"){
        window.location.href = "/pages/admin/dashboardAdmin.html";
    }else{
        window.location.href = "/pages/usuario/dashboard.html";
    }
}