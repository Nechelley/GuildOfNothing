package com.study.guildOfNothing.general.configuration.validation.exception;

public class FormErrorException extends Exception {

	private String field;
	private String error;

	public FormErrorException(String field, String error) {
		this.field = field;
		this.error = error;
	}

	public String getField() {
		return field;
	}

	public String getError() {
		return error;
	}

}
