package com.study.guildOfNothing.service.onlyInterface;

import com.study.guildOfNothing.general.configuration.validation.exception.HeroInBattleException;
import com.study.guildOfNothing.model.Hero;
import com.study.guildOfNothing.general.configuration.validation.exception.EntityNonExistentForManipulateException;
import com.study.guildOfNothing.general.configuration.validation.exception.FormErrorException;
import com.study.guildOfNothing.general.configuration.validation.exception.LimitHeroesCreatedException;
import com.study.guildOfNothing.general.configuration.validation.exception.TryingManipulateAnotherUserStuffException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HeroService {

	Page<Hero> getHeroesByUser(Pageable pageable);

	Hero createHero(Hero hero) throws FormErrorException, LimitHeroesCreatedException;

	Hero updateHero(Hero hero) throws EntityNonExistentForManipulateException, TryingManipulateAnotherUserStuffException, FormErrorException, HeroInBattleException;

	Hero getHero(Long id);

	void deleteHero(Long id) throws EntityNonExistentForManipulateException, TryingManipulateAnotherUserStuffException;
}
