package com.se1dhe.redqueen.bot.handler;

import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import com.se1dhe.redqueen.bot.core.handlers.IGroupMessageHandler;
import com.se1dhe.redqueen.bot.core.util.BotUtil;
import com.se1dhe.redqueen.bot.service.DbUserService;
import com.se1dhe.redqueen.bot.service.WordService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
public class SaveWordHandler implements IGroupMessageHandler {
    private final WordService wordService;
    private final DbUserService dbUserService;

    public SaveWordHandler(WordService wordService, DbUserService dbUserService) {
        this.wordService = wordService;
        this.dbUserService = dbUserService;
    }


    @Override
    public boolean onGroupMessage(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException {
        if (message.hasText()) {
            String[] words = message.getText().replaceAll(",", "").split(" ");

            for (String word : words) {
                wordService.addWod(word);

            }


            if (dbUserService.findById(message.getFrom().getId()) == null) {
                dbUserService.create(message.getFrom().getId(), message.getFrom().getFirstName(), message.getFrom().getLanguageCode(), true);
            }

        }

        return false;
    }
}
