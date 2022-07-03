package com.se1dhe.redqueen.bot.repository;


import com.se1dhe.redqueen.bot.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
    Word findByWord(String word);
}
