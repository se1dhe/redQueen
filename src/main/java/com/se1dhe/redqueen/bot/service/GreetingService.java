package com.se1dhe.redqueen.bot.service;


import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import com.se1dhe.redqueen.bot.core.util.BotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.groupadministration.RestrictChatMember;
import org.telegram.telegrambots.meta.api.methods.groupadministration.UnbanChatMember;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.ChatPermissions;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class GreetingService {

    private final DbUserService dbUserService;


    @Autowired
    public GreetingService(DbUserService dbUserService) {
        this.dbUserService = dbUserService;
    }


    public void deleteSystemMsg(AbstractTelegramBot bot, Message message) throws TelegramApiException {
        RestrictChatMember restrictChatMember = new RestrictChatMember();
        ChatPermissions chatPermissions = new ChatPermissions();

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        InlineKeyboardButton ship = new InlineKeyboardButton();
        ship.setText(LocalisationService.getString("greeting.button1"));
        ship.setCallbackData("ship");
        InlineKeyboardButton polyanica = new InlineKeyboardButton();
        polyanica.setText(LocalisationService.getString("greeting.button2"));
        polyanica.setCallbackData("polyanica");
        InlineKeyboardButton human = new InlineKeyboardButton();
        human.setText(LocalisationService.getString("greeting.button3"));
        human.setCallbackData("human");
        InlineKeyboardButton kremlBot = new InlineKeyboardButton();
        kremlBot.setText(LocalisationService.getString("greeting.button4"));
        kremlBot.setCallbackData("kremlBot");
        rowInline1.add(ship);
        rowInline1.add(polyanica);
        rowInline2.add(human);
        rowInline2.add(kremlBot);
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);

        message.setReplyMarkup(markupInline);
        DeleteMessage deleteMessage = new DeleteMessage();
        if (!message.getNewChatMembers().isEmpty()) {
            human.setCallbackData("human");
            markupInline.setKeyboard(rowsInline);
            chatPermissions.setCanSendMessages(false);
            restrictChatMember.setChatId(String.valueOf(message.getChatId()));
            restrictChatMember.setPermissions(chatPermissions);
            restrictChatMember.setUserId(message.getNewChatMembers().get(0).getId());
            bot.execute(restrictChatMember);
            deleteMessage.setChatId(String.valueOf(message.getChatId()));
            deleteMessage.setMessageId(message.getMessageId());
            bot.execute(deleteMessage);
            BotUtil.sendHtmlMessage(bot, message, String.format(LocalisationService.getString("greeting.helloMessage"), message.getNewChatMembers().get(0).getId(), message.getNewChatMembers().get(0).getFirstName()), false, markupInline);

        }

        if (message.getLeftChatMember() != null) {
            deleteMessage.setChatId(String.valueOf(message.getChatId()));
            deleteMessage.setMessageId(message.getMessageId());
            bot.execute(deleteMessage);
            if (dbUserService.findById(message.getLeftChatMember().getId()) != null) {
                dbUserService.delete(message.getLeftChatMember().getId());
            }
            BotUtil.sendHtmlMessage(bot, message, String.format(LocalisationService.getString("greeting.byeMessage"), message.getLeftChatMember().getId(), message.getLeftChatMember().getFirstName()), false, null);

        }
    }


    public void botCheck(AbstractTelegramBot bot, Update update, CallbackQuery query) throws TelegramApiException, InterruptedException {
        RestrictChatMember restrictChatMember = new RestrictChatMember();
        ChatPermissions chatPermissions = new ChatPermissions();
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(query.getId());
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(query.getMessage().getChatId()));
        deleteMessage.setMessageId(query.getMessage().getMessageId());
        UnbanChatMember unbanChatMember = new UnbanChatMember();
        unbanChatMember.setChatId(String.valueOf(query.getMessage().getChatId()));
        if (dbUserService.findById(query.getFrom().getId()) == null) {
            if (query.getData().equals("human")) {
                dbUserService.create(query.getFrom().getId(), query.getFrom().getFirstName(), query.getFrom().getLanguageCode(), true);
                chatPermissions.setCanSendPolls(true);
                chatPermissions.setCanSendMessages(true);
                chatPermissions.setCanSendOtherMessages(true);
                chatPermissions.setCanSendMediaMessages(true);
                chatPermissions.setCanPinMessages(true);
                chatPermissions.setCanInviteUsers(true);
                chatPermissions.setCanAddWebPagePreviews(true);
                restrictChatMember.setChatId(String.valueOf(query.getMessage().getChatId()));
                restrictChatMember.setUserId(query.getFrom().getId());
                restrictChatMember.setPermissions(chatPermissions);
                bot.execute(restrictChatMember);
                answerCallbackQuery.setText(LocalisationService.getString("antiBot.successful"));
                bot.execute(deleteMessage);
            } else {
                Instant time = Instant.now();
                unbanChatMember.setUserId(query.getFrom().getId());
                answerCallbackQuery.setText(LocalisationService.getString("antiBot.fail"));
                bot.execute(unbanChatMember);
            }
        } else {
            answerCallbackQuery.setText(LocalisationService.getString("antiBot.wrongUser"));

        }
        bot.execute(answerCallbackQuery);

    }
}