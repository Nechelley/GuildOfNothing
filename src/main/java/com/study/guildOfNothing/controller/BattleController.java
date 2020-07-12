package com.study.guildOfNothing.controller;

import com.study.guildOfNothing.controller.dto.in.BattleInDto;
import com.study.guildOfNothing.controller.dto.in.CharacterActionInDto;
import com.study.guildOfNothing.controller.dto.out.BattleOutDto;
import com.study.guildOfNothing.general.configuration.validation.exception.BattleNotOccurringException;
import com.study.guildOfNothing.general.configuration.validation.exception.TryingManipulateAnotherUserStuffException;
import com.study.guildOfNothing.model.Battle;
import com.study.guildOfNothing.general.configuration.validation.exception.FormErrorException;
import com.study.guildOfNothing.model.CharacterAction;
import com.study.guildOfNothing.service.onlyInterface.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/battle")
public class BattleController {

	@Autowired
	private BattleService battleService;

	@PostMapping
	public ResponseEntity<BattleOutDto> create(@RequestBody @Valid BattleInDto battleInDto, UriComponentsBuilder uriBuilder) throws FormErrorException, TryingManipulateAnotherUserStuffException {
		Battle battle = battleInDto.createBattle();

		Battle battleCreated = battleService.createBattle(battle);

		URI uri = uriBuilder.path("/battle/{id}").buildAndExpand(battleCreated.getId()).toUri();
		return ResponseEntity.created(uri).body(new BattleOutDto(battleCreated));
	}

	@GetMapping("/{id}")
	public ResponseEntity<BattleOutDto> findOne(@PathVariable Long id) {
		Battle battle = battleService.getBattle(id);

		if (battle == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(new BattleOutDto(battle));
	}

	@PutMapping("/{id}/action")
	public ResponseEntity<BattleOutDto> doAction(@PathVariable Long id, @RequestBody @Valid CharacterActionInDto characterActionInDto) throws FormErrorException, TryingManipulateAnotherUserStuffException, BattleNotOccurringException {
		CharacterAction characterAction = characterActionInDto.createCharacterAction();

		Battle battleUpdated = battleService.doCharacterActionOnBattle(new Battle(id), characterAction);

		if (battleUpdated == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(new BattleOutDto(battleUpdated));
	}

}
