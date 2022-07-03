package com.se1dhe.redqueen.bot.core.handlers;

import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface IGroupMessageHandler extends ITelegramHandler {
    /**
     * Fired whenever someone posts message within a group
     *
     * @param bot     the bot
     * @param update  the update
     * @param message the message
     * @return {@code true} if handler 'consumed' that event, aborting notification to other handlers, {@code false} otherwise, continuing to look for handler that would return {@code true}
     * @throws TelegramApiException the exception
     */
    boolean onGroupMessage(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException;
}