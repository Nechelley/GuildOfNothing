package com.study.guildOfNothing.general.configuration.validation.handle;

import com.study.guildOfNothing.general.configuration.validation.exception.LimitHeroesCreatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.study.guildOfNothing.model.User.LIMIT_HERO_CREATOR;

@RestControllerAdvice
public class LimitHeroesCreatedExceptionHandler {

	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(LimitHeroesCreatedException.class)
	public ResponseEntity<Object> handle() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The limit of heroes per user is "+LIMIT_HERO_CREATOR+".");
	}

}