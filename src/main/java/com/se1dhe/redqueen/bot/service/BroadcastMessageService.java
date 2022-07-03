package com.se1dhe.redqueen.bot.service;


import com.se1dhe.redqueen.bot.model.BroadcastMessage;
import com.se1dhe.redqueen.bot.model.DbUser;
import com.se1dhe.redqueen.bot.repository.BroadcastMessageRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BroadcastMessageService {
    private final BroadcastMessageRepository broadcastMessageRepository;

    public BroadcastMessageService(BroadcastMessageRepository broadcastMessageRepository) {
        this.broadcastMessageRepository = broadcastMessageRepository;
    }


    public List<BroadcastMessage> findBySend(boolean send) {
        return broadcastMessageRepository.findBySend(send);
    }

    public void create(BroadcastMessage message) {
        broadcastMessageRepository.save(message);
    }

    public void update(BroadcastMessage message) {
        broadcastMessageRepository.save(message);
    }

}
