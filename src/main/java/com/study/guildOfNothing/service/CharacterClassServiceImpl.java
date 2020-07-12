package com.study.guildOfNothing.service;

import com.study.guildOfNothing.constant.CharacterClassEnum;
import com.study.guildOfNothing.model.CharacterClass;
import com.study.guildOfNothing.repository.CharacterClassRepository;
import com.study.guildOfNothing.service.onlyInterface.CharacterClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterClassServiceImpl implements CharacterClassService {

	@Autowired
	private CharacterClassRepository characterClassRepository;

	public CharacterClass getCharacterClass(Long id) {
		Optional<CharacterClass> characterClass = characterClassRepository.findById(id);

		if (!characterClass.isPresent())
			return null;

		return characterClass.get();
	}

	@Override
	public CharacterClass getRandomCharacterClass() {
		CharacterClassEnum ramdomCharacterClassEnum = CharacterClassEnum.getRandom();
		return getCharacterClass(ramdomCharacterClassEnum.getId());
	}

}
