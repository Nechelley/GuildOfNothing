package com.study.guildOfNothing.service.onlyInterface;

import com.study.guildOfNothing.model.CharacterClass;

import java.util.Optional;

public interface CharacterClassService {

	Optional<CharacterClass> getCharacterClass(Long id);

	CharacterClass getRandomCharacterClass();

}
