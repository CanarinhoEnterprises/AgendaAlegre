async function cadastrarEspaco() {

    try {

        // ============================
        // CADASTRAR ENDEREÇO
        // ============================

        const endereco = {

            idCidade: Number(document.getElementById("idCidade").value),

            logradouro: document.getElementById("logradouro").value,

            bairro: document.getElementById("bairro").value,

            cep: document.getElementById("cep").value,

            num: Number(document.getElementById("numero").value),

            comp: document.getElementById("complemento").value,

            referencia: document.getElementById("referencia").value

        };

        const respostaEndereco = await fetch("/enderecos", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(endereco)

        });

        if (!respostaEndereco.ok) {

            alert("Erro ao cadastrar endereço!");

            return;

        }

        const enderecoCadastrado = await respostaEndereco.json();

        // ============================
        // CADASTRAR ESPAÇO
        // ============================

        const espaco = {

            idEndereco: enderecoCadastrado.idEndereco,

            idTipoEspaco: Number(document.getElementById("idTipoEspaco").value),

            capacidade: Number(document.getElementById("capacidade").value),

            nome: document.getElementById("nome").value,

            descricao: document.getElementById("descricao").value,

            urlCapa: document.getElementById("urlCapa").value,

            status: document.getElementById("status").value,

            idsRecursosAcessibilidade: []

        };

        const respostaEspaco = await fetch("/espacos", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(espaco)

        });

        if (respostaEspaco.ok) {

            alert("Espaço cadastrado com sucesso!");

            document.getElementById("formCadastroEspaco").reset();

            // Se quiser voltar para a listagem:
            // window.location.href = "espacoAdmin.html";

        } else {

            const erro = await respostaEspaco.text();

            console.log(erro);

            alert("Erro ao cadastrar espaço!");

        }

    } catch (erro) {

        console.error(erro);

        alert("Erro ao conectar com o servidor.");

    }

}

document.getElementById("formCadastroEspaco").addEventListener("submit", async function (event) {

    event.preventDefault();

    await cadastrarEspaco();

});