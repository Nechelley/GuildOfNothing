package com.study.guildOfNothing.controller.dto.in;

import com.study.guildOfNothing.model.Battle;
import com.study.guildOfNothing.model.Hero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BattleInDto {

	@NotNull
	private Long heroId;

	public Battle createBattle() {
		Battle battle = new Battle();
		battle.setHero(new Hero(heroId));

		return battle;
	}

}
