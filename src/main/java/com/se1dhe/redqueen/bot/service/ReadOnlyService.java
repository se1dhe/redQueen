package com.se1dhe.redqueen.bot.service;


import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import com.se1dhe.redqueen.bot.core.util.BotUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.groupadministration.RestrictChatMember;
import org.telegram.telegrambots.meta.api.objects.ChatPermissions;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.se1dhe.redqueen.util.Parser.dateParser;


@Service
@Log4j2
public class ReadOnlyService {


    public void setReadOnly(AbstractTelegramBot bot, Message message, int time, String unit) throws TelegramApiException {
        Instant banTime = Instant.now();
        if (unit.equals("s")) {
            banTime = Instant.now().plus(time, ChronoUnit.SECONDS);
        }
        if (unit.equals("m")) {
            banTime = Instant.now().plus(time, ChronoUnit.MINUTES);
        }
        if (unit.equals("h")) {
            banTime = Instant.now().plus(time, ChronoUnit.HOURS);
        }
        if (unit.equals("d")) {
            banTime = Instant.now().plus(time, ChronoUnit.DAYS);
        }
        if (unit.equals("m")) {
            banTime = Instant.now().plus(time, ChronoUnit.MONTHS);
        }

        if (message.getFrom().getId() == 1259547081) {
            Message replyMessage = message.getReplyToMessage();
            RestrictChatMember restrictChatMember = new RestrictChatMember();
            ChatPermissions chatPermissions = new ChatPermissions();
            chatPermissions.setCanSendMessages(false);
            restrictChatMember.setChatId(String.valueOf(message.getChatId()));
            restrictChatMember.setPermissions(chatPermissions);
            restrictChatMember.setUserId(replyMessage.getFrom().getId());
            restrictChatMember.setUntilDate(Math.toIntExact(banTime.getEpochSecond()));
            log.info(message.getFrom().getId());

            bot.execute(restrictChatMember);
            BotUtil.sendHtmlMessage(bot, message, String.format(LocalisationService.getString("readOnly.restrictMessage"), replyMessage.getFrom().getId(), replyMessage.getFrom().getFirstName(), dateParser(banTime)), false, null);
        } else
            BotUtil.sendHtmlMessage(bot, message, LocalisationService.getString("readOnly.noAccess"), true, null);
    }


    public void getReadOnly(AbstractTelegramBot bot, Message message) throws TelegramApiException {
        if (message.getFrom().getId() == 1259547081) {
            Message replyMessage = message.getReplyToMessage();
            RestrictChatMember restrictChatMember = new RestrictChatMember();
            ChatPermissions chatPermissions = new ChatPermissions();
            chatPermissions.setCanSendMessages(true);
            chatPermissions.setCanAddWebPagePreviews(true);
            chatPermissions.setCanInviteUsers(true);
            chatPermissions.setCanPinMessages(true);
            chatPermissions.setCanSendMediaMessages(true);
            chatPermissions.setCanSendOtherMessages(true);
            chatPermissions.setCanSendPolls(true);
            restrictChatMember.setChatId(String.valueOf(message.getChatId()));
            restrictChatMember.setPermissions(chatPermissions);
            restrictChatMember.setUserId(replyMessage.getFrom().getId());

            bot.execute(restrictChatMember);
            BotUtil.sendHtmlMessage(bot, message, String.format(LocalisationService.getString("readOnly.unRestrictMessage"), replyMessage.getFrom().getId(), replyMessage.getFrom().getFirstName()), false, null);
        } else
            BotUtil.sendHtmlMessage(bot, message, LocalisationService.getString("readOnly.noAccess"), true, null);

    }

    public void deleteMsg(AbstractTelegramBot bot, Message message) throws TelegramApiException {
        if (message.getFrom().getId() == 1259547081) {
            BotUtil.deleteMessage(bot, message.getReplyToMessage());
            BotUtil.deleteMessage(bot, message);
        } else
            BotUtil.sendHtmlMessage(bot, message, LocalisationService.getString("readOnly.noAccess"), true, null);

    }


}
