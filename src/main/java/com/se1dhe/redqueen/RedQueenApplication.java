package com.se1dhe.redqueen;

import com.se1dhe.redqueen.bot.core.DefaultTelegramBot;
import com.se1dhe.redqueen.bot.handler.*;
import com.se1dhe.redqueen.bot.service.*;
import com.se1dhe.redqueen.util.Config;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@EnableTransactionManagement
@Log4j2
public class RedQueenApplication {

    private static WordService wordService;
    private static ReadOnlyService readOnlyService;
    private static GreetingService greetingService;
    private static DbUserService dbUserService;
    private static GroupMessageService groupMessageService;
    private static BroadcastMessageService broadcastMessageService;


    public static DefaultTelegramBot telegramBot;

    @Autowired
    public RedQueenApplication(WordService wordService, ReadOnlyService readOnlyService, GreetingService greetingService, DbUserService dbUserService, GroupMessageService groupMessageService, BroadcastMessageService broadcastMessageService) {
        RedQueenApplication.wordService = wordService;
        RedQueenApplication.readOnlyService = readOnlyService;
        RedQueenApplication.greetingService = greetingService;
        RedQueenApplication.dbUserService = dbUserService;
        RedQueenApplication.groupMessageService = groupMessageService;
        RedQueenApplication.broadcastMessageService = broadcastMessageService;
    }

    public static void main(String[] args) throws TelegramApiException {
        Config.load();
        SpringApplication.run(RedQueenApplication.class, args);
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        // Register the default bot with token and username
        telegramBot = new DefaultTelegramBot(Config.BOT_TOKEN, Config.BOT_NAME);
        telegramBotsApi.registerBot(telegramBot);

        // Register access level validator
        telegramBot.setAccessLevelValidator(new AccessLevelValidator(dbUserService));

        telegramBot.addHandler(new GroupHandler(groupMessageService,dbUserService));
        telegramBot.addHandler(new ReadOnlyHandler(readOnlyService));
       // telegramBot.addHandler(new SaveWordHandler(wordService, dbUserService));
        //telegramBot.addHandler(new GreetingHandler(greetingService));
        telegramBot.addHandler(new GroupHandler(groupMessageService, dbUserService));
        telegramBot.addHandler(new InfoHandler(dbUserService));
        telegramBot.addHandler(new ReputationHandler(dbUserService));
        telegramBot.addHandler(new BroadcastMessageHandler(dbUserService, broadcastMessageService));
        telegramBot.addHandler(new DickHandler(dbUserService));


    }

}
