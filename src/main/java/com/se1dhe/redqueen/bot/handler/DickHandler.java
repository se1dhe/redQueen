package com.se1dhe.redqueen.bot.handler;


import com.se1dhe.redqueen.bot.core.AbstractTelegramBot;
import com.se1dhe.redqueen.bot.core.handlers.IGroupMessageHandler;
import com.se1dhe.redqueen.bot.core.util.BotUtil;
import com.se1dhe.redqueen.bot.model.DbUser;
import com.se1dhe.redqueen.bot.service.DbUserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
public class DickHandler implements IGroupMessageHandler {
    private final DbUserService dbUserService;

    public DickHandler(DbUserService dbUserService) {
        this.dbUserService = dbUserService;
    }

    @Override
    public boolean onGroupMessage(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException {
        if (dbUserService.findById(message.getFrom().getId()) == null) {
            dbUserService.create(message.getFrom().getId(), message.getFrom().getFirstName(), message.getFrom().getLanguageCode(), true);
        }
        String messageText = message.getText();
        DbUser user = dbUserService.findById(message.getFrom().getId());
        String userName;
        if (message.getFrom().getUserName() != null) {
            userName = message.getFrom().getUserName();
        } else userName = message.getFrom().getFirstName();

        if (messageText.equalsIgnoreCase("/dick")) {
            if (user.isPlayed()) {
                BotUtil.sendMessage(bot, message, userName + ", Ты сегодня уже отращивал.\nЗанимайся размерами после полуночи :D", true, false, null);
                return false;
            }
            final int size = getRandomNumber(-5, 10);
            if (user.getPenisSize() + size < 1) {
                user.setPenisSize(0);
            }
            else {
                user.setPenisSize(user.getPenisSize() + size);
            }
            user.setPlayed(true);
            dbUserService.update(user);

            if (size == 0) {
                BotUtil.sendMessage(bot, message, userName + ", твой писюн остался прежних размеров\nПродолжай отращивать после полуночи.", true, false, null);
                return false;
            }

            if (user.getPenisSize() < 1) {
                BotUtil.sendMessage(bot, message, userName + ", теперь у тебя нет писюна :(\nПродолжай отращивать после полуночи.", true, false, null);
                return false;
            }

            if (size > 0) {
                BotUtil.sendMessage(bot, message, userName + ", твой писюн вырос на "+ String.valueOf(size).replace("-","") +" см\uD83D\uDC4D\n" +
                        "Теперь его длинна: " + user.getPenisSize() + " см.\nПродолжай отращивать после полуночи.", true, false, null);
            } else {
                BotUtil.sendMessage(bot, message, userName + ", твой писюн уменьшился на "+ String.valueOf(size).replace("-","") +" см\uD83D\uDC4E\uD83C\uDFFF\n" +
                        "Теперь его длинна: " + user.getPenisSize() + " см.\nПродолжай отращивать после полуночи.", true, false, null);
            }

        }

        if (messageText.equalsIgnoreCase("/top")) {
            StringBuilder sb = new StringBuilder();
            List<DbUser> userList = dbUserService.findByPenisSize();
            int i = 1;

            sb.append("<b>\uD83D\uDD1EТоп писюнов канала</b>").append("\n\n");

            for (DbUser dbUser : userList) {
                if (dbUser.getPenisSize() > 0) {
                    sb.append(i).append(".  ").append(dbUser.getName()).append(" — ").append(dbUser.getPenisSize()).append(" см.\n");

                } else {
                    sb.append(i).append(".  ").append(dbUser.getName()).append(" — ").append(" нет писюна.\n");

                }
                i++;
            }

            BotUtil.sendHtmlMessage(bot, message, sb.toString(), true,  null);

        }

        return false;
    }



    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
