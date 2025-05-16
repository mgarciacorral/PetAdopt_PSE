window.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const idChat = urlParams.get("idChat");

    if (!idChat) {
        console.log("No hay idChat en la URL, no se conecta al WebSocket");
        return;  // Sale y no crea conexión WebSocket
    }

    const socket = new WebSocket("ws://localhost:8080/PetAdopt_PSE/chat/" + idChat);

    socket.onopen = function () {
        console.log("Conectado al WebSocket del chat id: " + idChat);
    };

    socket.onmessage = function (event) {
        const panel = document.getElementById("chatForm:mensajesPanel");
        const newMessage = document.createElement("div");
        newMessage.textContent = event.data;
        panel.appendChild(newMessage);
    };

    window.enviarMensaje = function () {
        const input = document.getElementById("chatForm:mensajeInput");
        const mensaje = input.value;
        console.log("Mensaje a enviar:", mensaje);
        if (mensaje.trim() === "") {
            console.log("Mensaje vacío, no se envía.");
            return; // no enviar mensajes vacíos
        }
        socket.send(mensaje);
        input.value = "";
    };

});
