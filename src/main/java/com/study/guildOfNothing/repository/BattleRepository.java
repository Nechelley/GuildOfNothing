package com.study.guildOfNothing.repository;

import com.study.guildOfNothing.model.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {

	Optional<Battle> getBattleByHeroIdAndOccurringIsTrue(Long heroId);

}
