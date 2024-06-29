package edu.tomek.chatserver.controllel;

import edu.tomek.chatserver.model.Message;
import edu.tomek.chatserver.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("v1/msg")
public class ChatServController {
    @Autowired
    ChatService chatService;

    @PostMapping
    public ResponseEntity<Message> postMessage(@RequestParam("author") String author, @RequestParam("content") String content){
        return ResponseEntity.ok(chatService.postMessage(author, content));
    }

    @GetMapping
    public ResponseEntity<Iterable<Message>> getMessages(
            @RequestParam(value = "from_id", required = false) Long fromId,
            @RequestParam(value = "to_id", required = false) Long toId,
            @RequestParam(value = "from_time", required = false) ZonedDateTime fromDateTime,
            @RequestParam(value = "to_time", required = false) ZonedDateTime toDateTime){
        return ResponseEntity.ok(this.chatService.getMessages(fromId, toId, fromDateTime, toDateTime));
    }
}
