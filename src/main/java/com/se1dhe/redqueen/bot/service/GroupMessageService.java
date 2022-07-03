package com.se1dhe.redqueen.bot.service;


import com.se1dhe.redqueen.bot.model.DbUser;
import com.se1dhe.redqueen.bot.model.GroupMessage;
import com.se1dhe.redqueen.bot.repository.GroupMessageRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class GroupMessageService {

    private final GroupMessageRepository groupMessageRepository;

    public GroupMessageService(GroupMessageRepository groupMessageRepository) {
        this.groupMessageRepository = groupMessageRepository;
    }

    public void create(GroupMessage groupMessage) {
        groupMessageRepository.save(groupMessage);
    }

    public List<GroupMessage> findByWord(String message) {
        List<GroupMessage> allMessage = groupMessageRepository.findAll();
        List<GroupMessage> result = new ArrayList<GroupMessage>();
        for (GroupMessage wordMessage : allMessage) {
            if (wordMessage.getMessage().contains(message)) {
                result.add(wordMessage);
            }
        }
        return result;

    }

    public boolean exists(String message) {
        return groupMessageRepository.findByMessage(message) != null;
    }

    public void addMessage(String message) {
        GroupMessage _message = new GroupMessage();
        if (!exists(message)) {
            _message.setMessage(message);
            create(_message);
        }
    }

    public GroupMessage findByMessage(String message) {
        return groupMessageRepository.findByMessage(message);
    }


}
