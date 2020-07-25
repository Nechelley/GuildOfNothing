package com.study.guildOfNothing.repository;

import com.study.guildOfNothing.model.BattleAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleActionRepository extends JpaRepository<BattleAction, Long> {
}
