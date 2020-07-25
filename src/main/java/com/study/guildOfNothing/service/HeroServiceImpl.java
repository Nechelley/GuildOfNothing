package com.study.guildOfNothing.service;

import com.study.guildOfNothing.general.configuration.validation.exception.HeroInBattleException;
import com.study.guildOfNothing.model.CharacterClass;
import com.study.guildOfNothing.model.Hero;
import com.study.guildOfNothing.model.Race;
import com.study.guildOfNothing.repository.HeroRepository;
import com.study.guildOfNothing.general.configuration.validation.exception.EntityNonExistentForManipulateException;
import com.study.guildOfNothing.general.configuration.validation.exception.FormErrorException;
import com.study.guildOfNothing.general.configuration.validation.exception.LimitHeroesCreatedException;
import com.study.guildOfNothing.general.configuration.validation.exception.TryingManipulateAnotherUserStuffException;
import com.study.guildOfNothing.model.User;
import com.study.guildOfNothing.service.onlyInterface.CharacterClassService;
import com.study.guildOfNothing.service.onlyInterface.HeroService;
import com.study.guildOfNothing.service.onlyInterface.RaceService;
import com.study.guildOfNothing.service.onlyInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class HeroServiceImpl implements HeroService {

	private final HeroRepository heroRepository;

	private final CharacterClassService characterClassService;
	private final UserService userService;
	private final RaceService raceService;

	@Autowired
	public HeroServiceImpl(HeroRepository heroRepository, CharacterClassService characterClassService, UserService userService, RaceService raceService) {
		this.heroRepository = heroRepository;
		this.characterClassService = characterClassService;
		this.userService = userService;
		this.raceService = raceService;
	}

	@Override
	public Page<Hero> getHeroesByUser(Pageable pageable) {
		User userLogged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return heroRepository.findAllByUserId(userLogged.getId(), pageable);
	}

	@Override
	@Transactional
	public Hero createHero(Hero hero) throws FormErrorException, LimitHeroesCreatedException {
		User userLogged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		hero.setUser(userLogged);

		if (userLogged.getHeroes().size() == User.LIMIT_HERO_CREATOR)
			throw new LimitHeroesCreatedException();

		Optional<Race> race = raceService.getRace(hero.getRace().getId());
		if (!race.isPresent())
			throw new FormErrorException("raceId", "Invalid race");

		hero.setRace(race.get());

		Optional<CharacterClass> heroClass = characterClassService.getCharacterClass(hero.getCharacterClass().getId());
		if (!heroClass.isPresent())
			throw new FormErrorException("characterClassId", "Invalid class");

		hero.setCharacterClass(heroClass.get());
		hero.initializeNewCharacterByRaceAndClass(race.get(), heroClass.get());

		return heroRepository.save(hero);
	}

	@Override
	@Transactional
	public Hero updateHero(Hero hero) throws EntityNonExistentForManipulateException, TryingManipulateAnotherUserStuffException, FormErrorException, HeroInBattleException {
		Optional<Hero> heroInDatabase = heroRepository.findById(hero.getId());
		if (!heroInDatabase.isPresent())
			throw new EntityNonExistentForManipulateException();

		Hero heroToUpdate = heroInDatabase.get();

		Optional<Hero> heroInBattle = heroRepository.getHeroInBattle(hero.getId());
		if (heroInBattle.isPresent())
			throw new HeroInBattleException();

		userService.testIfUserTryingManipulateAnotherUser(heroToUpdate.getUser());

		if (!heroToUpdate.checkIfPointsAreDistributedCorrect(hero.getBaseCharacterAttributes()))
			throw new FormErrorException("attributes", "Invalid attributes, count the points again please");

		heroToUpdate.distributeAvailablePoints(hero.getBaseCharacterAttributes());

		return heroRepository.save(heroToUpdate);
	}

	@Override
	public Optional<Hero> getHero(Long id) {
		return heroRepository.findById(id);
	}

	@Override
	@Transactional
	public void deleteHero(Long id) throws EntityNonExistentForManipulateException, TryingManipulateAnotherUserStuffException {
		Optional<Hero> heroInDatabase = heroRepository.findById(id);
		if (!heroInDatabase.isPresent())
			throw new EntityNonExistentForManipulateException();

		userService.testIfUserTryingManipulateAnotherUser(heroInDatabase.get().getUser());

		heroRepository.deleteById(id);
	}

}
