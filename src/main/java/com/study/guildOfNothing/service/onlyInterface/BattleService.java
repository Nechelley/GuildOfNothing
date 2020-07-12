package com.study.guildOfNothing.service.onlyInterface;

import com.study.guildOfNothing.general.configuration.validation.exception.BattleNotOccurringException;
import com.study.guildOfNothing.general.configuration.validation.exception.FormErrorException;
import com.study.guildOfNothing.general.configuration.validation.exception.TryingManipulateAnotherUserStuffException;
import com.study.guildOfNothing.model.Battle;
import com.study.guildOfNothing.model.CharacterAction;

public interface BattleService {

	Battle createBattle(Battle battle) throws FormErrorException, TryingManipulateAnotherUserStuffException;

	Battle getBattle(Long id);

	Battle doCharacterActionOnBattle(Battle battle, CharacterAction characterAction) throws TryingManipulateAnotherUserStuffException, BattleNotOccurringException, FormErrorException;

	Battle getBattleWithHeroOcurring(Long heroId);

}
