package com.study.guildOfNothing.service;

import com.study.guildOfNothing.model.Character;
import com.study.guildOfNothing.repository.CharacterRepository;
import com.study.guildOfNothing.service.onlyInterface.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterServiceImpl implements CharacterService {

	private final CharacterRepository characterRepository;

	@Autowired
	public CharacterServiceImpl(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

	@Override
	public List<Character> getCharacters(List<Character> characters) {
		List<Long> ids = characters
				.stream()
				.map(Character::getId)
				.collect(Collectors.toList());
		return characterRepository.findAllById(ids);
	}

}
