package com.study.guildOfNothing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@EqualsAndHashCode(callSuper = true)
@Entity
@PrimaryKeyJoinColumn(name = "character_id")
@Data
public class Hero extends Character {

	@ManyToOne
	private User user;

	public Hero() {
		super();
		setHero(true);
	}

	public Hero(Long id) {
		super(id);
		setHero(true);
	}

	public void recoverAllLifeAndActionPoints() {
		setLife(getBaseCharacterAttributes().getConstitution()*Character.LIFE_MULTIPLIER);
		setAvailableActionPoints(Character.INITIAL_ACTION_POINTS);
	}

}
