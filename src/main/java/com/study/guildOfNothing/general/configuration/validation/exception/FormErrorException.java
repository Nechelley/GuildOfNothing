package com.study.guildOfNothing.general.configuration.validation.exception;

import lombok.Getter;

@Getter
public class FormErrorException extends Exception {

	private String field;
	private String error;

	public FormErrorException(String field, String error) {
		this.field = field;
		this.error = error;
	}

}
