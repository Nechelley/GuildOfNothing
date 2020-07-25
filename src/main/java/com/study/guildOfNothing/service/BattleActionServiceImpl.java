package com.study.guildOfNothing.service;

import com.study.guildOfNothing.model.BattleAction;
import com.study.guildOfNothing.repository.BattleActionRepository;
import com.study.guildOfNothing.service.onlyInterface.BattleActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BattleActionServiceImpl implements BattleActionService {

	private final BattleActionRepository characterActionRepository;

	@Autowired
	public BattleActionServiceImpl(BattleActionRepository characterActionRepository) {
		this.characterActionRepository = characterActionRepository;
	}

	@Override
	public BattleAction getCharacterAction(Long id) {
		Optional<BattleAction> characterActionOptional = characterActionRepository.findById(id);

		if (!characterActionOptional.isPresent())
			return null;

		return characterActionOptional.get();
	}

}
