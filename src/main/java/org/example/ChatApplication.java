package org.example;
import java.util.ArrayList;
import java.util.List;

interface ChatRoom {
    void addObserver(User user);
    void removeObserver(User user);
    void sendMessage(String message, User sender);
}

class ConcreteChatRoom implements ChatRoom {
    private List<User> users = new ArrayList<>();

    @Override
    public void addObserver(User user) {
        users.add(user);
    }

    @Override
    public void removeObserver(User user) {
        users.remove(user);
    }

    @Override
    public void sendMessage(String message, User sender) {
        for (User user : users) {
            if (user != sender) {
                user.receiveMessage(message);
            }
        }
    }
}


interface User {
    void sendMessage(String message);
    void receiveMessage(String message);
}

class ConcreteUser implements User {
    private String name;
    private ChatRoom chatRoom;

    public ConcreteUser(String name, ChatRoom chatRoom) {
        this.name = name;
        this.chatRoom = chatRoom;
        chatRoom.addObserver(this);
    }

    @Override
    public void sendMessage(String message) {
        chatRoom.sendMessage(message, this);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println(name + " received message: " + message);
    }
}

public class ChatApplication {
    public static void main(String[] args) {
        ChatRoom chatRoom = new ConcreteChatRoom();

        User user1 = new ConcreteUser("Nurasyl", chatRoom);
        User user2 = new ConcreteUser("Aziz", chatRoom);
        User user3 = new ConcreteUser("Erasyl", chatRoom);

        user1.sendMessage("Как ваши дела?");
        user2.sendMessage("Отлично, а у вас?");
        user3.sendMessage("Супер!");
    }
}
