package com.study.guildOfNothing.service.onlyInterface;

import com.study.guildOfNothing.model.Battle;
import com.study.guildOfNothing.model.Enemy;

public interface EnemyService {

	Enemy createRandomEnemyForBattle(Battle battle);

}
