package com.se1dhe.redqueen.bot.handler;

import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import com.se1dhe.redqueen.bot.core.handlers.IGroupMessageHandler;
import com.se1dhe.redqueen.util.Config;
import com.se1dhe.redqueen.util.anekdotRu.AnekdotParser;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

public class MemHandler implements IGroupMessageHandler {
    @Override
    public boolean onGroupMessage(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException, IOException {

        String messageText = message.getText();

        if (messageText.equalsIgnoreCase("/mem")) {
            AnekdotParser.download();
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto(new InputFile((new File("./img/picture.jpg"))));
            sendPhoto.setChatId(Config.GROUP_ID);
            bot.execute(sendPhoto);
            return false;
        }
        return false;
    }
}
