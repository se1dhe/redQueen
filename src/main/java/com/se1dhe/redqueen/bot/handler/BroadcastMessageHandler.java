package com.se1dhe.redqueen.bot.handler;

import com.se1dhe.redqueen.bot.core.handlers.IAccessLevelValidator;
import com.se1dhe.redqueen.bot.core.handlers.ITelegramHandler;
import com.se1dhe.redqueen.bot.core.handlers.inline.*;
import com.se1dhe.redqueen.bot.core.handlers.inline.events.InlineCallbackEvent;
import com.se1dhe.redqueen.bot.core.handlers.inline.events.InlineMessageEvent;
import com.se1dhe.redqueen.bot.core.util.BotUtil;
import com.se1dhe.redqueen.bot.model.BroadcastMessage;
import com.se1dhe.redqueen.bot.service.BroadcastMessageService;
import com.se1dhe.redqueen.bot.service.DbUserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


@Service
public class BroadcastMessageHandler extends AbstractInlineHandler implements IAccessLevelValidator {

    private final DbUserService dbUserService;
    private final BroadcastMessageService broadcastMessageService;

    public BroadcastMessageHandler(DbUserService dbUserService, BroadcastMessageService broadcastMessageService) {
        this.dbUserService = dbUserService;
        this.broadcastMessageService = broadcastMessageService;
    }

    @Override
    public void registerMenu(InlineContext ctx, InlineMenuBuilder builder) {
        builder
                .name("Добавить сообщение")
                .button(new InlineButtonBuilder(ctx)
                        .name("Отправить сообщение")
                        .row(0)
                        .onQueryCallback(this::handleAddMessage)
                        .onInputMessage(this::handleAddMessage)
                        .build())
                .button(defaultClose(ctx))
                .build();
    }

    @Override
    public String getCommand() {
        return "/send";
    }

    @Override
    public String getUsage() {
        return "/send";
    }

    @Override
    public String getDescription() {
        return "/send";
    }

    @Override
    public boolean validate(ITelegramHandler handler, User user) {
        return dbUserService.validate(user.getId(), getRequiredAccessLevel());
    }


    @Override
    public int getRequiredAccessLevel() {
        return 10;
    }

    private boolean handleAddMessage(InlineCallbackEvent event) throws TelegramApiException {
        final InlineUserData userData = event.getContext().getUserData(event.getQuery().getFrom().getId());
        if (userData.getState() == 0) {
            userData.setState(1);
            BotUtil.editMessage(event.getBot(), event.getQuery().getMessage(), "Введите ваше сообщение!", false, null);
            return true;
        }
        return false;
    }

    private boolean handleAddMessage(InlineMessageEvent event) throws TelegramApiException {
        final InlineUserData userData = event.getContext().getUserData(event.getMessage().getFrom().getId());
        BroadcastMessage bMessage = new BroadcastMessage();
        final AbsSender bot = event.getBot();
        final Message message = event.getMessage();


        if (userData.getState() == 1) {
            final String sendMsg = message.getText();
            if (sendMsg.isEmpty()) {
                BotUtil.sendMessage(bot, message, "Поле не должно быть пустым!", false, false, null);
                return true;
            }

            BotUtil.sendMessage(bot, message, "Сообщение добавлено", false, false, null);
            bMessage.setAuthor(message.getFrom().getFirstName());
            bMessage.setMessage(sendMsg);
            bMessage.setSend(true);
            broadcastMessageService.create(bMessage);
            event.getContext().clear(message.getFrom().getId());
            return true;
        }

        return false;
    }

    public InlineKeyboardMarkup test() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        WebAppInfo webAppInfo = WebAppInfo.builder().url("https://localhost/test.php").build();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        List<InlineKeyboardButton> row = new ArrayList<>();
        inlineKeyboardButton.setText("test");
        inlineKeyboardButton.setWebApp(webAppInfo);
        row.add(inlineKeyboardButton);

        rowList.add(row);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }


}
