const API_URL = "http://localhost:8081";

async function post(url, dados){

    const resposta = await fetch(API_URL + url, {
        method: "POST",
        headers:{
            "Content-Type":"application/json"
        },
        body: JSON.stringify(dados)
    });

    return resposta;
}