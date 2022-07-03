package com.se1dhe.redqueen.bot.handler;

import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import com.se1dhe.redqueen.bot.core.handlers.IGroupMessageHandler;
import com.se1dhe.redqueen.bot.core.util.BotUtil;
import com.se1dhe.redqueen.bot.model.DbUser;
import com.se1dhe.redqueen.bot.model.GroupMessage;
import com.se1dhe.redqueen.bot.service.DbUserService;
import com.se1dhe.redqueen.bot.service.GroupMessageService;
import com.se1dhe.redqueen.bot.service.LocalisationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Date;
import java.util.List;
import java.util.Random;


@Log4j2
@Service
public class GroupHandler implements IGroupMessageHandler {
    private final GroupMessageService groupMessageService;
    private final DbUserService dbUserService;

    public GroupHandler(GroupMessageService groupMessageService, DbUserService dbUserService) {
        this.groupMessageService = groupMessageService;
        this.dbUserService = dbUserService;

    }


    @Override
    public boolean onGroupMessage(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException {
        DbUser user = dbUserService.findById(message.getFrom().getId());
        Date date = new Date();
        Random rand = new Random();
        GroupMessage groupMessage = new GroupMessage();
        if (message.hasText()) {
            user.setMessageCount(user.getMessageCount() + 1);
            groupMessage.setMessage(message.getText());
            groupMessage.setAuthor(dbUserService.findById(message.getFrom().getId()).getName());
            groupMessage.setDateCreated(date);
            groupMessage.setChatId(message.getChatId());
            groupMessageService.create(groupMessage);
            dbUserService.update(user);
            if (message.getText().startsWith("RQ, ")) {
                String snd = message.getText().replaceAll("RQ, ", "");
                List<GroupMessage> answerList = groupMessageService.findByWord(snd);
                int rnd = rand.nextInt(answerList.size());
                String answer = answerList.get(rnd).getMessage();
                String author = answerList.get(rnd).getAuthor();
                BotUtil.sendMessage(bot, message, answer, true, false, null);

            }
        }


        return false;
    }


}
