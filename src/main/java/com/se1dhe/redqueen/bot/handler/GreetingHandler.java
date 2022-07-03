package com.se1dhe.redqueen.bot.handler;

import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import com.se1dhe.redqueen.bot.core.handlers.ICallbackQueryHandler;
import com.se1dhe.redqueen.bot.core.handlers.IGroupMessageHandler;
import com.se1dhe.redqueen.bot.service.DbUserService;
import com.se1dhe.redqueen.bot.service.GreetingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
@Log4j2
public class GreetingHandler implements IGroupMessageHandler, ICallbackQueryHandler {

    private final GreetingService greetingService;


    public GreetingHandler(GreetingService greetingService) {
        this.greetingService = greetingService;
    }


    @Override
    public boolean onGroupMessage(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException {

        greetingService.deleteSystemMsg(bot, message);

        return false;
    }


    @Override
    public boolean onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query) throws TelegramApiException {
        try {
            greetingService.botCheck(bot, update, query);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}

