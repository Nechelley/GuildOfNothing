package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.Race;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RaceOutDto {

	private Long id;
	private String name;

	public RaceOutDto(Race race) {
		this.id = race.getId();
		this.name = race.getName();
	}

}
