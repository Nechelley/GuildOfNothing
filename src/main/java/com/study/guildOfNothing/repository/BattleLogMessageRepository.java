package com.study.guildOfNothing.repository;

import com.study.guildOfNothing.model.BattleLogMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleLogMessageRepository extends JpaRepository<BattleLogMessage, Long> {
}
