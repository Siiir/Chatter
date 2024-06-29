package edu.tomek.chatserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatServerApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(ChatServerApplication.class, args);
//        for (var msg : context.getBean(ChatService.class).getMessages(null, null, null, null)){
//            System.out.println(msg);
//        };
    }

}
