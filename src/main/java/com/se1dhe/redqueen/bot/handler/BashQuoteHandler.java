package com.se1dhe.redqueen.bot.handler;

import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import com.se1dhe.redqueen.bot.core.handlers.IGroupMessageHandler;
import com.se1dhe.redqueen.bot.core.util.BotUtil;
import com.se1dhe.redqueen.util.bashOrg.BashOrgParser;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class BashQuote implements IGroupMessageHandler {

    @Override
    public boolean onGroupMessage(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException, IOException {
        String messageText = message.getText();

        if (messageText.equalsIgnoreCase("/quote")) {
            BotUtil.sendMessage(bot, message, BashOrgParser.getQuote(), false, false, null);
            return false;
        }
        return false;
    }
}
