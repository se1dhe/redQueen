package com.se1dhe.redqueen.bot.model;

import lombok.*;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DbUser extends User {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;


    private String name;
    private String lang;
    private boolean human;
    private int messageCount = 0;
    private int reputation = 10;
    private Integer level;
    private Integer penisSize = 0;
    private boolean played;


}
