package com.study.guildOfNothing.service.onlyInterface;

import com.study.guildOfNothing.model.CharacterClass;

public interface CharacterClassService {

	CharacterClass getCharacterClass(Long id);

	CharacterClass getRandomCharacterClass();

}
