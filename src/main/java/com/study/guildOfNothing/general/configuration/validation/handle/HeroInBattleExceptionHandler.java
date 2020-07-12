package com.study.guildOfNothing.general.configuration.validation.handle;

import com.study.guildOfNothing.general.configuration.validation.exception.HeroInBattleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HeroInBattleExceptionHandler {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HeroInBattleException.class)
	public ResponseEntity<Object> handle() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The hero is in a battle.");
	}

}