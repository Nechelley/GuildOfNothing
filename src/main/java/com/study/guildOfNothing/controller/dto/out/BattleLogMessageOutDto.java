package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.BattleLogMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BattleLogMessageOutDto {

	private String text;

	BattleLogMessageOutDto(BattleLogMessage battleLogMessage) {
		text = battleLogMessage.getText();
	}

	public static List<BattleLogMessageOutDto> createDtoFromBattleLogMessageList(List<BattleLogMessage> battleLogMessages) {
		List<BattleLogMessageOutDto> battleLogMessageOutDtos = new ArrayList<>();
		battleLogMessages.forEach(
				battleLogMessage -> battleLogMessageOutDtos.add(new BattleLogMessageOutDto(battleLogMessage))
		);
		return battleLogMessageOutDtos;
	}

}
