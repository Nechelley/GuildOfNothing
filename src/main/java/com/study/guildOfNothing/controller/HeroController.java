package com.study.guildOfNothing.controller;

import com.study.guildOfNothing.controller.dto.in.HeroInDto;
import com.study.guildOfNothing.controller.dto.out.HeroOutDto;
import com.study.guildOfNothing.general.configuration.validation.exception.HeroInBattleException;
import com.study.guildOfNothing.model.Hero;
import com.study.guildOfNothing.general.configuration.validation.exception.EntityNonExistentForManipulateException;
import com.study.guildOfNothing.general.configuration.validation.exception.FormErrorException;
import com.study.guildOfNothing.general.configuration.validation.exception.LimitHeroesCreatedException;
import com.study.guildOfNothing.general.configuration.validation.exception.TryingManipulateAnotherUserStuffException;
import com.study.guildOfNothing.general.dtoGroup.OnCreate;
import com.study.guildOfNothing.service.onlyInterface.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/hero")
@Validated
public class HeroController {

	@Autowired
	private HeroService heroService;

	@GetMapping
	public Page<HeroOutDto> getAllByUser(@PageableDefault(sort = "name", direction = Direction.ASC) Pageable pageable) {
		return HeroOutDto.createDtoFromHeroesList(heroService.getHeroesByUser(pageable));
	}

	@PostMapping
	@Validated(OnCreate.class)
	public ResponseEntity<HeroOutDto> create(@RequestBody @Valid HeroInDto heroInDto, UriComponentsBuilder uriBuilder) throws FormErrorException, LimitHeroesCreatedException {
		Hero hero = heroInDto.createHero();

		Hero heroCreated = heroService.createHero(hero);

		URI uri = uriBuilder.path("/hero/{id}").buildAndExpand(heroCreated.getId()).toUri();
		return ResponseEntity.created(uri).body(new HeroOutDto(heroCreated));
	}

	@PutMapping("/{id}")
	public ResponseEntity<HeroOutDto> update(@PathVariable Long id, @RequestBody @Valid HeroInDto heroInDto) throws EntityNonExistentForManipulateException, TryingManipulateAnotherUserStuffException, FormErrorException, HeroInBattleException {
		Hero hero = heroInDto.createHero();
		hero.setId(id);
		Hero heroUpdated = heroService.updateHero(hero);

		return ResponseEntity.ok(new HeroOutDto(heroUpdated));
	}

}
