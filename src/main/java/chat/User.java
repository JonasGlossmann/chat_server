package chat;

import java.time.Duration;
import java.time.LocalDateTime;

public class User {
    private LocalDateTime lastSeen;
    boolean active = true;
    private final String name;

    public User(String name) {
        lastSeen = LocalDateTime.now();
        this.name = name;
    }

    public boolean isActive(){
        LocalDateTime now = LocalDateTime.now();

        long minutes = Duration.between(now,lastSeen).toMinutes();
        return minutes<5&&active;
    }

    public void livingSign(){
        lastSeen = LocalDateTime.now();
    }

    public void setRemove(){
        this.active = false;
    }

    public String getName(){
        return this.name;
    }
}
