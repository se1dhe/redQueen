package com.se1dhe.redqueen.bot.service;


import com.se1dhe.redqueen.bot.model.Word;
import com.se1dhe.redqueen.bot.repository.WordRepository;
import org.springframework.stereotype.Service;

@Service
public class WordService {

    private final WordRepository wordRepository;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public void create(Word word) {
        wordRepository.save(word);
    }

    public boolean exists(String word) {
        return wordRepository.findByWord(word) != null;
    }

    public void addWod(String word) {
        Word _word = new Word();
        if (!exists(word)) {
            _word.setWord(word);
            create(_word);
        }
    }


}
