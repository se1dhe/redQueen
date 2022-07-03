package com.se1dhe.redqueen.bot.service;


import com.se1dhe.redqueen.bot.core.handlers.IAccessLevelValidator;
import com.se1dhe.redqueen.bot.core.handlers.ITelegramHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class AccessLevelValidator implements IAccessLevelValidator {
    private final DbUserService userService;

    public AccessLevelValidator(DbUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean validate(ITelegramHandler handler, User user) {
        return userService.validate(user.getId(), handler.getRequiredAccessLevel());
    }
}