package com.study.guildOfNothing.service;

import com.study.guildOfNothing.model.AttackAction;
import com.study.guildOfNothing.model.Character;
import com.study.guildOfNothing.model.CharacterAction;
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
	public CharacterAction getCharacterAction(Long id) {
		Optional<CharacterAction> characterActionOptional = characterActionRepository.findById(id);

		if (!characterActionOptional.isPresent())
			return null;

		return characterActionOptional.get();
	}

	@Override
	public int doCharacterAction(Character attacker, Character defender, CharacterAction characterAction) {
		attacker.payActionCostFor(characterAction);

		int damage = ((AttackAction) characterAction).getDamageCalculed(attacker, defender);
		defender.takeDamage(damage);

		return damage;
	}

}
