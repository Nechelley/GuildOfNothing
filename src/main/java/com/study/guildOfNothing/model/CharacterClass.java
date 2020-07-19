package com.study.guildOfNothing.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CharacterClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToMany
	@JoinTable(name="character_class_x_character_action",
			joinColumns = {@JoinColumn(name="character_class_id")},
			inverseJoinColumns = {@JoinColumn(name="character_action_id")})
	private List<CharacterAction> initialCharacterActions;

	public CharacterClass(Long id) {
		this.id = id;
	}

	public List<CharacterAction> getInitialCharacterActionsCopy() {
		List<CharacterAction> characterActionsCopy = new ArrayList<>();
		characterActionsCopy.addAll(initialCharacterActions);
		return characterActionsCopy;
	}
}
