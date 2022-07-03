package com.se1dhe.redqueen.bot.core.handlers;

import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface IChatJoinMember extends ITelegramHandler {


    boolean onChatJoinMember(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException;
}
