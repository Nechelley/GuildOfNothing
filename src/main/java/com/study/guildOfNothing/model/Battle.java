package com.study.guildOfNothing.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Battle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Hero hero;
	@OneToMany(mappedBy = "battle", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BattleLogMessage> battleLogMessages;
	@OneToOne
	private Enemy enemy;
	@OneToOne
	private Character characterTurn;
	private boolean occurring;
	@ManyToOne
	private Character winner;

	public Battle(Long id) {
		this.id = id;
	}

}
