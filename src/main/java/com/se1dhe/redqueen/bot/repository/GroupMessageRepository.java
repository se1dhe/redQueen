package com.se1dhe.redqueen.bot.repository;

import com.se1dhe.redqueen.bot.model.GroupMessage;
import com.se1dhe.redqueen.bot.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long> {
    GroupMessage findByMessage(String word);
}
