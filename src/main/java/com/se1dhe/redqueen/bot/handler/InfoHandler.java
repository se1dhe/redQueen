package com.se1dhe.redqueen.bot.handler;

import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import com.se1dhe.redqueen.bot.core.handlers.IGroupMessageHandler;
import com.se1dhe.redqueen.bot.model.entity.Command;
import com.se1dhe.redqueen.bot.service.DbUserService;
import com.se1dhe.redqueen.util.Config;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;


@Service
public class InfoHandler implements IGroupMessageHandler {

    private final DbUserService dbUserService;

    public InfoHandler(DbUserService dbUserService) {
        this.dbUserService = dbUserService;

    }

    @Override
    public boolean onGroupMessage(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException {
        GetChatAdministrators chatAdministrators = new GetChatAdministrators();
        List<ChatMember> chatMembers = bot.execute(new GetChatAdministrators(String.valueOf(message.getChatId())));
        User creator = new User();
        StringBuilder admin = new StringBuilder();
        for (ChatMember chatMember : chatMembers) {
            if (chatMember.getStatus().equals("creator")) {
                creator = chatMember.getUser();
            } else
                admin.append("<b><a href=\"tg://user?id=").append(chatMember.getUser().getId()).append("\">").append(chatMember.getUser().getFirstName()).append("</a></b> | ");
        }
        if (message.getText() != null && message.getText().equals(Command.INFO.getCommandName())) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(String.valueOf(message.getChatId()));
            sendPhoto.setPhoto(new InputFile(new File("img/photo_2021-03-14_21-52-30.jpg")));
            sendPhoto.setParseMode("HTML");
            sendPhoto.setCaption("\uD83D\uDD30Красная Королева (англ. RED QUEEN) является очень продвинутым и самосознательным искусственным интеллектом. Она выполняет 3 задачи:\n" +
                    "\n" +
                    "\uD83D\uDD30Управление и защита каналов.\n" +
                    "\uD83D\uDD30Защита жизней подписчиков.\n" +
                    "\uD83D\uDD30Третья задача неизвестна, т.к.  разработчик уничтожил экран компьютера.\n" +
                    "\uD83D\uDD30About: Красная королева способна сама принимать решения в критических ситуациях, иногда даже в обход своих директив.\n\n" +
                    "✅<code>Имя бота: </code><b>" + Config.BOT_NAME + "</b>\n" +
                    "✅<code>Имя группы: </code><b>" + message.getChat().getTitle() + "</b>\n" +
                    "✅<code>Создатель группы: </code><b><a href=\"tg://user?id=" + creator.getId() + "\">" + creator.getFirstName() + "</a></b>" + "\n" +
                    "✅<code>Лидер репутации: </code><b><a href=\"tg://user?id=" + dbUserService.findByReputationCount().get(0).getId() + "\">" + dbUserService.findByReputationCount().get(0).getName() + "</a></b><code>(" + dbUserService.findByReputationCount().get(0).getReputation() + ")</code>" + "\n" +
                    "✅<code>Лидер сообщений: </code><b><a href=\"tg://user?id=" + dbUserService.findByMessageCount().get(0).getId() + "\">" + dbUserService.findByMessageCount().get(0).getName() + "</a></b><code>(" + dbUserService.findByMessageCount().get(0).getMessageCount() + ")</code>\n" +
                    "✅<code>Лидер писюнов: </code><b><a href=\"tg://user?id=" + dbUserService.findByPenisSize().get(0).getId() + "\">" + dbUserService.findByPenisSize().get(0).getName() + "</a></b><code>(" + dbUserService.findByPenisSize().get(0).getPenisSize() + ")</code>\n" +
                    "✅<code>Администраторы: </code>" + admin +
                    "\n✅<code>Создатель бота: </code><b><a href=\"tg://user?id=" + 1259547081 + "\">" + "se1dhe" + "</a></b>\n");

            bot.execute(sendPhoto);

        }


        return false;
    }


}
