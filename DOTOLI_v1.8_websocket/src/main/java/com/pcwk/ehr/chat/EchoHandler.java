package com.pcwk.ehr.chat;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class EchoHandler extends TextWebSocketHandler {

    // Store sessions by user ID
    private Map<String, WebSocketSession> userSessions = new HashMap<>();

    // Handle new connections
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection established with session: " + session.getId());

        // Send a prompt to the client to send their user ID
        session.sendMessage(new TextMessage("Please provide your user ID."));
    }

    // Handle messages from clients
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        // If the message starts with "setUserId:", it's the user ID setup message
        if (payload.startsWith("setUserId:")) {
            String userId = payload.substring(10);  // Extract user ID from the message
            userSessions.put(userId, session);  // Store the session for this user ID
            session.getAttributes().put("userId", userId);  // Save userId in session attributes

            // Inform the user that their ID has been set
            session.sendMessage(new TextMessage("User ID set to: " + userId));
            System.out.println(userId + " connected.");
        } else {
            // Handle regular messages (chat)
            String[] parts = payload.split(":", 2);
            String receiverId = parts[0]; // Extract receiver ID
            String msg = parts[1];  // Extract the message content

            // Send the message to the intended receiver
            WebSocketSession receiverSession = userSessions.get(receiverId);
            if (receiverSession != null && receiverSession.isOpen()) {
                receiverSession.sendMessage(new TextMessage(msg));
                System.out.println("Message from " + session.getId() + " to " + receiverId + ": " + msg);
            } else {
                System.out.println("Receiver not found or not connected.");
            }
        }
    }

    // Handle connection closure
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) {
            userSessions.remove(userId);
            System.out.println(userId + " disconnected.");
        }
    }
}
