package com.se1dhe.redqueen.bot.repository;

import com.se1dhe.redqueen.bot.model.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DbUserRepository extends JpaRepository<DbUser, Long> {
    @Query("SELECT u FROM DbUser u ORDER BY messageCount DESC")
    List<DbUser> findByMessageCountUniqueResult();

    @Query("SELECT u FROM DbUser u ORDER BY reputation DESC")
    List<DbUser> findByReputationCountUniqueResult();

    @Query("SELECT u FROM DbUser u ORDER BY penisSize DESC")
    List<DbUser> findByPenisSize();
}
