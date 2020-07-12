package com.study.guildOfNothing.general.configuration.validation.handle;

import com.study.guildOfNothing.general.configuration.validation.dto.FormErrorDto;
import com.study.guildOfNothing.general.configuration.validation.exception.FormErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class FormErrorHandler {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(FormErrorException.class)
	public List<FormErrorDto> handle(FormErrorException exception) {
		List<FormErrorDto> formErrors = new ArrayList<>();

		formErrors.add(new FormErrorDto(exception.getField(), exception.getError()));

		return formErrors;
	}

}
