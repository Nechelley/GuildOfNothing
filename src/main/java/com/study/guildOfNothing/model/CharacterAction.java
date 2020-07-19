package com.study.guildOfNothing.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
public class CharacterAction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int costActionPoints;

	public static final int EXIT_ID = 1;
	public static final int PASS_TIME_ID = 2;

	public boolean isStandartAction() {
		return id == EXIT_ID || id == PASS_TIME_ID;
	}

}
