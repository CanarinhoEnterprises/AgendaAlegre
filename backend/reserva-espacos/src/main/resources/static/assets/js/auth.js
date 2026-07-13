function pegarToken(){

    return localStorage.getItem("token");

}


function pegarPayload(){

    const token = pegarToken();

    if(!token){
        return null;
    }

    const partes = token.split(".");

    const payload = partes[1];

    return JSON.parse(atob(payload));
}


function pegarRoles(){

    const payload = pegarPayload();

    if(!payload){
        return [];
    }

    return payload.roles || [];
}


function possuiRole(role){

    return pegarRoles().includes(role);

}

function logout(){

    localStorage.removeItem("token");

    window.location.href = "../landingpage.html";

}