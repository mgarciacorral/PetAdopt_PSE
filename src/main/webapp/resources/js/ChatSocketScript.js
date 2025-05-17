window.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const idChat = urlParams.get("idChat");

    if (!idChat) {
        console.log("No hay idChat en la URL, no se conecta al WebSocket");
        return;
    }

    const socket = new WebSocket("ws://localhost:8080/PetAdopt_PSE/chat/" + idChat);
    const emailUsuario = document.getElementById('chatForm:usuarioEmail').value;

    socket.onopen = function () {
        console.log("Conectado al WebSocket del chat id: " + idChat);
    };

    socket.onmessage = function (event) {
        try {
            const data = JSON.parse(event.data);
            const esPropio = data.remitente === emailUsuario;
            mostrarMensaje(data.contenido, esPropio);
        } catch (e) {
            console.error("Error al procesar mensaje recibido:", e);
        }
    };


    window.enviarMensaje = function () {
        const input = document.getElementById("chatForm:mensajeInput");
        const mensaje = input.value.trim();

        if (mensaje === "") {
            console.log("Mensaje vacío, no se envía.");
            return;
        }

        socket.send(JSON.stringify({
            contenido: mensaje,
            remitente: emailUsuario
        }));

        guardarMensajeEnBD(mensaje, emailUsuario, idChat);
        input.value = "";
    };

    cargarMensajesDesdeBD(idChat, emailUsuario);
});


function mostrarMensaje(contenido, esPropio) {
    const panel = document.getElementById("chatForm:mensajesPanel");

    const newMessage = document.createElement("div");
    newMessage.style.marginBottom = "8px";
    newMessage.style.padding = "6px 10px";
    newMessage.style.borderRadius = "8px";
    newMessage.style.maxWidth = "80%";
    newMessage.style.wordWrap = "break-word";
    newMessage.style.alignSelf = esPropio ? "flex-end" : "flex-start";
    newMessage.style.backgroundColor = esPropio ? "#d1ecf1" : "#f0f0f0";
    newMessage.textContent = contenido;

    panel.appendChild(newMessage);
    panel.scrollTop = panel.scrollHeight;
}



function guardarMensajeEnBD(contenido, remitente, idChat) {
    const mensaje = {
        contenido: contenido,
        remitente: remitente, 
        fechaEnvio: new Date().toISOString(),
        idChat: Number(idChat)
    };

    console.log("Payload que enviamos:", mensaje);

    fetch("http://localhost:8080/PetAdopt_PSE/webresources/mensaje", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(mensaje)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error("Error al guardar el mensaje: " + text);
                });
            } else {
                console.log("Mensaje guardado correctamente en la BD");
            }
        })
        .catch(error => {
            console.error("Error en guardarMensajeEnBD:", error.message);
        });
}


function cargarMensajesDesdeBD(idChat, emailUsuario) {
    fetch(`http://localhost:8080/PetAdopt_PSE/webresources/mensaje/mensajes/${idChat}`)
            .then(response => {
                if (!response.ok)
                    throw new Error("Error al cargar los mensajes");
                return response.json();
            })
            .then(mensajes => {
                mensajes.forEach(msg => {
                    const esPropio = msg.remitente === emailUsuario;
                    mostrarMensaje(msg.contenido, esPropio);
                });
            })
            .catch(error => console.error("Error al cargar mensajes:", error));
}

