package com.se1dhe.redqueen.bot.service;


import com.se1dhe.redqueen.bot.model.DbUser;
import com.se1dhe.redqueen.bot.model.GroupMessage;
import com.se1dhe.redqueen.bot.repository.DbUserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DbUserService {
    private final DbUserRepository dbUserRepository;

    public DbUserService(DbUserRepository dbUserRepository) {
        this.dbUserRepository = dbUserRepository;
    }

    @Transactional
    public void create(Long id, String name, String lang, boolean human) {
        final DbUser user = new DbUser(id, name, lang, human, 0, 10, 0,0,false);
        dbUserRepository.save(user);
    }

    @Transactional
    public DbUser findById(Long id) {
        return dbUserRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Long id) {
        dbUserRepository.delete(findById(id));
    }

    @Transactional
    public void update(DbUser user) {
        dbUserRepository.save(user);
    }

    @Transactional
    public List<DbUser> findByMessageCount() {
        return dbUserRepository.findByMessageCountUniqueResult();
    }

    @Transactional
    public List<DbUser> findByReputationCount() {
        return dbUserRepository.findByReputationCountUniqueResult();
    }

    @Transactional

    public boolean validate(Long id, int level) {
        if (level == 0) {
            return true;
        }

        final DbUser user = findById(id);
        return (user != null) && (user.getLevel() >= level);
    }


    public List<DbUser> findAll() {
        return dbUserRepository.findAll();
    }

    public List<DbUser> findByPenisSize() {
        return dbUserRepository.findByPenisSize();
    }
}
