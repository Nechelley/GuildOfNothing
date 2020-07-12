package com.study.guildOfNothing.service;

import com.study.guildOfNothing.general.configuration.validation.exception.HeroInBattleException;
import com.study.guildOfNothing.model.Battle;
import com.study.guildOfNothing.model.CharacterClass;
import com.study.guildOfNothing.model.Hero;
import com.study.guildOfNothing.repository.HeroRepository;
import com.study.guildOfNothing.general.configuration.validation.exception.EntityNonExistentForManipulateException;
import com.study.guildOfNothing.general.configuration.validation.exception.FormErrorException;
import com.study.guildOfNothing.general.configuration.validation.exception.LimitHeroesCreatedException;
import com.study.guildOfNothing.general.configuration.validation.exception.TryingManipulateAnotherUserStuffException;
import com.study.guildOfNothing.model.User;
import com.study.guildOfNothing.service.onlyInterface.BattleService;
import com.study.guildOfNothing.service.onlyInterface.CharacterClassService;
import com.study.guildOfNothing.service.onlyInterface.HeroService;
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

	@Autowired
	private HeroRepository heroRepository;

	@Autowired
	private CharacterClassService characterClassService;

	@Autowired
	private UserService userService;

	@Autowired
	private BattleService battleService;

	public Page<Hero> getHeroesByUser(Pageable pageable) {
		User userLogged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return heroRepository.findAllByUserId(userLogged.getId(), pageable);
	}

	@Transactional
	public Hero createHero(Hero hero) throws FormErrorException, LimitHeroesCreatedException {
		User userLogged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		hero.setUser(userLogged);

		if (userLogged.getHeroes().size() == User.LIMIT_HERO_CREATOR)
			throw new LimitHeroesCreatedException();

		CharacterClass heroClass = characterClassService.getCharacterClass(hero.getCharacterClass().getId());
		if (heroClass == null)
			throw new FormErrorException("characterClassId", "Invalid class");

		hero.setCharacterClass(heroClass);
		hero.initializeNewCharacterByCharacterClass(heroClass);

		return heroRepository.save(hero);
	}

	@Transactional
	public Hero updateHero(Hero hero) throws EntityNonExistentForManipulateException, TryingManipulateAnotherUserStuffException, FormErrorException, HeroInBattleException {
		Optional<Hero> heroInDatabase = heroRepository.findById(hero.getId());
		if (heroInDatabase.isEmpty())
			throw new EntityNonExistentForManipulateException();

		Hero heroToUpdate = heroInDatabase.get();

		Battle battleWithHero = battleService.getBattleWithHeroOcurring(hero.getId());
		if (battleWithHero != null)
			throw new HeroInBattleException();

		userService.testIfUserTryingManipulateAnotherUser(heroToUpdate.getUser());

		if (!heroToUpdate.checkIfPointsAreDistributedCorrect(hero.getBaseCharacterAttributes()))
			throw new FormErrorException("heroAttributes", "Invalid heroAttributes, count the points again please");

		heroToUpdate.setName(hero.getName());
		heroToUpdate.distributeAvailablePoints(hero.getBaseCharacterAttributes());

		return heroRepository.save(heroToUpdate);
	}

	public Hero getHero(Long id) {
		Optional<Hero> hero = heroRepository.findById(id);

		if (!hero.isPresent())
			return null;

		return hero.get();
	}

}
