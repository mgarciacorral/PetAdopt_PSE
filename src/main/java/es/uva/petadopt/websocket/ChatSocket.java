package es.uva.petadopt.websocket;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{idChat}")
public class ChatSocket {

    private static final Map<Integer, Set<Session>> sesionesPorChat = new ConcurrentHashMap<>();

    private int idChat;

    @OnOpen
    public void onOpen(Session session, @PathParam("idChat") int idChat) {
        System.out.println("Conexión abierta para chat: " + idChat + ", sessionId: " + session.getId());
        this.idChat = idChat;
        sesionesPorChat.computeIfAbsent(idChat, k -> new HashSet<>()).add(session);
    }

    @OnMessage
    public void onMessage(String mensaje, Session session) {
        System.out.println("Mensaje recibido: '" + mensaje + "'");
        Set<Session> sesiones = sesionesPorChat.get(idChat);
        if (sesiones == null) {
            return;
        }
        for (Session s : sesiones) {
            if (s.isOpen()) {
                try {
                    s.getBasicRemote().sendText(mensaje);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Mensaje");

    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Cerrando");
        Set<Session> sesiones = sesionesPorChat.get(idChat);
        if (sesiones != null) {
            sesiones.remove(session);
        }

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }
}
