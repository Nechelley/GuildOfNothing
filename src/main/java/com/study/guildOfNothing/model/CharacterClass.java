package com.study.guildOfNothing.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
public class CharacterClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToMany
	@JoinTable(name="character_class_x_battle_action",
			joinColumns = {@JoinColumn(name="character_class_id")},
			inverseJoinColumns = {@JoinColumn(name="battle_action_id")})
	private List<BattleAction> initialBattleActions;

	public CharacterClass(Long id) {
		this.id = id;
	}

	public List<CharacterBattleActionRelationship> getInitialBattleActionsForNewCharacter(Character character) {
		return initialBattleActions.stream()
				.map(battleAction -> new CharacterBattleActionRelationship(character, battleAction))
				.collect(Collectors.toList());
	}
}
