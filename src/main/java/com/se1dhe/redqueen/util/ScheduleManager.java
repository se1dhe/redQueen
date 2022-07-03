package com.se1dhe.redqueen.util;


import com.se1dhe.redqueen.RedQueenApplication;
import com.se1dhe.redqueen.bot.core.util.BotUtil;
import com.se1dhe.redqueen.bot.model.BroadcastMessage;
import com.se1dhe.redqueen.bot.model.DbUser;
import com.se1dhe.redqueen.bot.service.BroadcastMessageService;
import com.se1dhe.redqueen.bot.service.DbUserService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@EnableScheduling
@Component
public class ScheduleManager {


    @Scheduled(initialDelay = 6000, fixedDelay = 3000)
    public void sendNotifications() throws TelegramApiException {
        broadcastSend(RedQueenApplication.telegramBot);
    }

    private final BroadcastMessageService broadcastMessageService;
    private final DbUserService dbUserService;

    public ScheduleManager(BroadcastMessageService broadcastMessageService,DbUserService dbUserService) {
        this.broadcastMessageService = broadcastMessageService;
        this.dbUserService = dbUserService;
    }


    public void broadcastSend(AbsSender bot) throws TelegramApiException {
        List<BroadcastMessage> broadcastMessageList = broadcastMessageService.findBySend(true);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);

        for (BroadcastMessage broadcastMessage : broadcastMessageList) {
            sendMessage.setText(broadcastMessage.getMessage());
            sendMessage.setChatId("@se1dhe_dev");
            bot.execute(sendMessage);
            broadcastMessage.setSend(false);
            broadcastMessageService.update(broadcastMessage);
        }
    }

    @Scheduled(cron="@midnight")
    public void playedScheduler() throws TelegramApiException {
        playedCheck(RedQueenApplication.telegramBot);
    }

    public void playedCheck(AbsSender bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdownV2(true);
        sendMessage.setText("`Пора отращивать песюны!`");
        sendMessage.setChatId("-1001792394355");
        bot.execute(sendMessage);
        List<DbUser> dbUserList = dbUserService.findAll();
        for (DbUser dbUser : dbUserList) {
            dbUser.setPlayed(false);
            dbUserService.update(dbUser);

        }
    }

}