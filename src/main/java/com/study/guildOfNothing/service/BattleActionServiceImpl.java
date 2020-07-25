package com.study.guildOfNothing.service;

import com.study.guildOfNothing.model.BattleAction;
import com.study.guildOfNothing.repository.CharacterActionRepository;
import com.study.guildOfNothing.service.onlyInterface.CharacterActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterActionServiceImpl implements CharacterActionService {

	@Autowired
	private CharacterActionRepository characterActionRepository;

	@Override
	public BattleAction getCharacterAction(Long id) {
		Optional<BattleAction> characterActionOptional = characterActionRepository.findById(id);

		if (!characterActionOptional.isPresent())
			return null;

		return characterActionOptional.get();
	}

}
