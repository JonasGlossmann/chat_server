package chat;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegistrationService extends TextWebSocketHandler {
    UserLibrarySingleton db = new UserLibrarySingleton();

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("Client connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String regex = "^([^:]+):(.*)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message.getPayload());



        if (matcher.matches()) {
            String id = matcher.group(1);
            String text = matcher.group(2);

            switch (id){
                case "getID":
                    UUID uuid = db.createUser(text);
                    if(session.isOpen()) session.sendMessage(new TextMessage(uuid.toString()));
                    break;
                default:

            }
        }
    }

    @Override
    public void afterConnectionClosed (WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("Client disconnected: " + session.getId());
    }


}
