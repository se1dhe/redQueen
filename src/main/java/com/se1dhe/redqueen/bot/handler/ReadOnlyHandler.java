package com.se1dhe.redqueen.bot.handler;

import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import com.se1dhe.redqueen.bot.core.handlers.IGroupMessageHandler;
import com.se1dhe.redqueen.bot.model.entity.Command;
import com.se1dhe.redqueen.bot.service.GroupMessageService;
import com.se1dhe.redqueen.bot.service.ReadOnlyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j2
@Service
public class ReadOnlyHandler implements IGroupMessageHandler {

    private final ReadOnlyService readOnlyService;

    public ReadOnlyHandler(ReadOnlyService readOnlyService) {
        this.readOnlyService = readOnlyService;
    }


    @Override
    public boolean onGroupMessage(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException {

        String text = message.getText();
        if (text != null) {
            String[] args = text.split(" ");
            if (message.getText().startsWith(Command.READ_ONLY.getCommandName())) {
                readOnlyService.setReadOnly(bot, message, Integer.parseInt(args[1]), args[2]);

            }
            if (message.getText().startsWith(Command.UN_READ_ONLY.getCommandName())) {
                readOnlyService.getReadOnly(bot, message);

            }
            if (message.getText().startsWith(Command.DELETE_MSG.getCommandName())) {
                readOnlyService.deleteMsg(bot, message);
            }


        }
        return false;
    }
}
