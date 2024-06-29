package edu.tomek.chatserver.service;

import edu.tomek.chatserver.model.Message;
import edu.tomek.chatserver.model.MessageRepo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    MessageRepo messageRepo;

    public Message postMessage(@NonNull String author, @NonNull String content) {
        var msg = new Message(author, ZonedDateTime.now(), content);
        return this.messageRepo.save(msg);
    }

    public Iterable<Message> getMessages(Long fromId, Long toId, ZonedDateTime fromDate, ZonedDateTime toDate) {
        if (fromId == null) {
            fromId = Long.MIN_VALUE;
        }
        if (toId == null) {
            toId = Long.MAX_VALUE;
        }
        if (fromDate == null) {
            fromDate = Optional.ofNullable(
                    this.messageRepo.findFirstMsg()).map(msg -> msg.getDateTime().minusSeconds(60)).orElseGet(() -> ZonedDateTime.now().minusSeconds(60));
        }
        if (toDate == null) {
            toDate = ZonedDateTime.now().plusSeconds(60);
        }

        return this.messageRepo.findMessagesByIdBetweenAndDateTimeBetween(fromId, toId, fromDate, toDate);
    }
}
