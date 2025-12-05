package chat;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public final class UserLibrarySingleton extends TextWebSocketHandler {
    private HashMap<UUID,User> userLibrary  = new HashMap<UUID, User>();

    public UUID createUser(String name){
        this.checkHeartbeat();
        User tmp = new User(name);
        UUID uuid = UUID.randomUUID();
        if(userLibrary.containsKey(uuid)) {
            throw new RuntimeException();
        }
        userLibrary.put(uuid,tmp);
        return uuid;
    }

    public void checkHeartbeat(){
        HashMap<UUID,User> tmp = new HashMap<UUID,User>();
        userLibrary.forEach((k,v)->{
            if (v.isActive()) tmp.put(k,v);
        });
        userLibrary = tmp;
    }

    public void deleteUser(UUID uuid){
        User tmp = userLibrary.get(uuid);
        tmp.setRemove();
    }

    public boolean containsUser(String name){
        for(HashMap.Entry<UUID, User> entry: userLibrary.entrySet()){
            if(entry.getValue().getName().equals(name)) return true;
        }
        return false;
    }

}
