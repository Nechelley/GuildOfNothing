package com.study.guildOfNothing.service.onlyInterface;

import com.study.guildOfNothing.model.Character;
import com.study.guildOfNothing.model.CharacterAction;

public interface CharacterActionService {

	CharacterAction getCharacterAction(Long id);

	int doCharacterAction(Character attacker, Character defender, CharacterAction characterAction);

}
