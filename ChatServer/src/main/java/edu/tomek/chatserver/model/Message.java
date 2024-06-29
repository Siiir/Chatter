package edu.tomek.chatserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Setter @Getter
public final class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String author;
    @NonNull @JsonProperty("date_time")
    private ZonedDateTime dateTime;
    @NonNull
    private String content;
}
