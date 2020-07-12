package com.study.guildOfNothing.controller.dto.in;

import com.study.guildOfNothing.model.Battle;
import com.study.guildOfNothing.model.Hero;

import javax.validation.constraints.NotNull;

public class BattleInDto {

	@NotNull
	private Long heroId;

	public Long getHeroId() {
		return heroId;
	}

	public void setHeroId(Long heroId) {
		this.heroId = heroId;
	}

	public Battle createBattle() {
		Battle battle = new Battle();
		battle.setHero(new Hero(heroId));

		return battle;
	}

}
