package com.study.guildOfNothing.general.configuration.validation.handle;

import com.study.guildOfNothing.general.configuration.validation.exception.EntityNonExistentForManipulateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EntityNonExistentForManipulateExceptionHandler {

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNonExistentForManipulateException.class)
	public ResponseEntity<Object> handle() {
		return ResponseEntity.notFound().build();
	}

}