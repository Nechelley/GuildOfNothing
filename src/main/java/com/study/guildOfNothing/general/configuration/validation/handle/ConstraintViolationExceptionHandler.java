package com.study.guildOfNothing.general.configuration.validation.handle;

import com.study.guildOfNothing.general.configuration.validation.dto.FormErrorDto;
import com.study.guildOfNothing.general.util.StringUtil;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestControllerAdvice
public class ConstraintViolationExceptionHandler {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public List<FormErrorDto> handle(ConstraintViolationException exception) {
		List<FormErrorDto> formErrors = new ArrayList<>();

		Iterator iterator = exception.getConstraintViolations().iterator();
		ConstraintViolationImpl constraintViolation;
		while(iterator.hasNext()) {
			constraintViolation = (ConstraintViolationImpl) iterator.next();
			formErrors.add(new FormErrorDto(StringUtil.getTextEndedAfterLastCharacter(constraintViolation.getPropertyPath().toString(), "."), constraintViolation.getMessage()));
		}

		return formErrors;
	}

}