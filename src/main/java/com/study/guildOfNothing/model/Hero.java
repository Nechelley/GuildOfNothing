package com.study.guildOfNothing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@EqualsAndHashCode(callSuper = true)
@Entity
@PrimaryKeyJoinColumn(name = "character_id")
@Data
@NoArgsConstructor
public class Hero extends Character {

	@ManyToOne
	private User user;

	public Hero(Long id) {
		super();
		setId(id);
	}

	public boolean isHero() {
		return true;
	}

	public void recover() {
		setLife(getBaseCharacterAttributes().getConstitution()*Character.LIFE_MULTIPLIER);
		setAvailableActionPoints(Character.INITIAL_ACTION_POINTS);
	}

}
