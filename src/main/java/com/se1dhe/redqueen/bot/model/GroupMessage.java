package com.se1dhe.redqueen.bot.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GroupMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String author;
    private String message;
    private Date dateCreated;
    private Long chatId;

    public boolean startWith(String word) {
        return message.contains(word);
    }
}
