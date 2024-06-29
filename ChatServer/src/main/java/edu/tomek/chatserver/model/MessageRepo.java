package edu.tomek.chatserver.model;

import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;

public interface MessageRepo extends CrudRepository<Message, Long> {
    Message findFirstBy();
    default Message findFirstMsg(){
        return this.findFirstBy();
    }
    Iterable<Message> findMessagesByIdBetweenAndDateTimeBetween(@NonNull Long fromId, @NonNull Long toId, @NonNull ZonedDateTime fromDate, @NonNull ZonedDateTime toDate);
}
