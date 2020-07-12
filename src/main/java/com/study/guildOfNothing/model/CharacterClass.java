package com.study.guildOfNothing.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CharacterClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@OneToOne(optional = false)
	private CharacterAttributes initialCharacterAttributes;
	@ManyToMany
	@JoinTable(name="character_class_x_character_action",
			joinColumns = {@JoinColumn(name="character_class_id")},
			inverseJoinColumns = {@JoinColumn(name="character_action_id")})
	private List<CharacterAction> initialCharacterActions;

	public CharacterClass() { }

	public CharacterClass(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CharacterAttributes getInitialCharacterAttributes() {
		return initialCharacterAttributes;
	}

	public void setInitialCharacterAttributes(CharacterAttributes initialCharacterAttributes) {
		this.initialCharacterAttributes = initialCharacterAttributes;
	}

	public List<CharacterAction> getInitialCharacterActions() {
		return initialCharacterActions;
	}

	public void setInitialCharacterActions(List<CharacterAction> initialCharacterActions) {
		this.initialCharacterActions = initialCharacterActions;
	}

	public List<CharacterAction> getInitialCharacterActionsCopy() {
		List<CharacterAction> characterActionsCopy = new ArrayList<>();
		initialCharacterActions.forEach(characterAction -> characterActionsCopy.add(characterAction));
		return characterActionsCopy;
	}
}
