package com.se1dhe.redqueen.bot.handler;

import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import com.se1dhe.redqueen.bot.core.handlers.IGroupMessageHandler;
import com.se1dhe.redqueen.bot.core.util.BotUtil;
import com.se1dhe.redqueen.bot.model.DbUser;
import com.se1dhe.redqueen.bot.model.entity.Command;
import com.se1dhe.redqueen.bot.service.DbUserService;
import com.se1dhe.redqueen.bot.service.LocalisationService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
public class ReputationHandler implements IGroupMessageHandler {
    private final DbUserService dbUserService;

    public ReputationHandler(DbUserService dbUserService) {
        this.dbUserService = dbUserService;
    }


    @Override
    public boolean onGroupMessage(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException {
        if (message.hasText()) {
            if (message.getText().equals(Command.UP_REPUTATION.getCommandName())) {
                Message replyMessage = message.getReplyToMessage();
                if (message.getFrom().getId().equals(replyMessage.getFrom().getId())) {
                    BotUtil.sendHtmlMessage(bot, message, LocalisationService.getString("reputation.self"), true, null);
                    return false;

                }
                DbUser user = dbUserService.findById(replyMessage.getFrom().getId());
                user.setReputation(user.getReputation() + 1);
                dbUserService.update(user);
                BotUtil.sendHtmlMessage(bot, message, String.format(LocalisationService.getString("reputation.up"), message.getFrom().getId(), message.getFrom().getFirstName(), user.getId(), user.getName()), false, null);
                return false;
            }
            if (message.getText().equals(Command.DAWN_REPUTATION.getCommandName())) {
                Message replyMessage = message.getReplyToMessage();
                if (message.getFrom().getId().equals(replyMessage.getFrom().getId())) {
                    BotUtil.sendHtmlMessage(bot, message, LocalisationService.getString("reputation.self"), true, null);
                    return false;

                }
                DbUser user = dbUserService.findById(replyMessage.getFrom().getId());
                user.setReputation(user.getReputation() + 1);
                dbUserService.update(user);
                BotUtil.sendHtmlMessage(bot, message, String.format(LocalisationService.getString("reputation.dawn"), message.getFrom().getId(), message.getFrom().getFirstName(), user.getId(), user.getName()), false, null);
                return false;
            }


        }

        return false;
    }
}