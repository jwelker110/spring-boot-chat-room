package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String msg) {
        onlineSessions.forEach((String key, Session val) -> {
            try {
                val.getBasicRemote().sendText(msg);
            } catch (Exception e) {
                System.out.println("An error occurred while trying to send object: " + e.getMessage());
            }
        });
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session) {
        onlineSessions.put(session.getId(), session);

        updateOnlineUsers(MessageType.ENTER);
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        Message message = JSON.parseObject(jsonStr, Message.class);
        message.setOnlineCount(onlineSessions.size());
        message.setType(MessageType.CHAT.toString());

        sendMessageToAll(message.toJsonString());
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        try {
            String id = session.getId();
            session.close();
            onlineSessions.remove(id);

            updateOnlineUsers(MessageType.LEAVE);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to close session: " + e.getMessage());
        }
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    private void updateOnlineUsers(MessageType messageType) {
        sendMessageToAll(new Message(null, null, onlineSessions.size(), messageType.toString()).toJsonString());
    }
}
