package com.study.guildOfNothing.service.onlyInterface;

import com.study.guildOfNothing.general.configuration.validation.exception.BattleNotOccurringException;
import com.study.guildOfNothing.general.configuration.validation.exception.FormErrorException;
import com.study.guildOfNothing.general.configuration.validation.exception.TryingManipulateAnotherUserStuffException;
import com.study.guildOfNothing.model.Battle;
import com.study.guildOfNothing.model.BattleAction;

import java.util.Optional;

public interface BattleService {

	Battle createBattle(Battle battle) throws FormErrorException, TryingManipulateAnotherUserStuffException;

	Optional<Battle> getBattle(Long id);

	Battle doBattleActionOnBattle(Battle battle, BattleAction battleAction) throws TryingManipulateAnotherUserStuffException, BattleNotOccurringException, FormErrorException;

	Optional<Battle> getBattleWithHeroOcurring(Long heroId);

}
