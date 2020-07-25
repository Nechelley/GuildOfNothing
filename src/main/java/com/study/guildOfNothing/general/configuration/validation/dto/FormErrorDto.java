package com.study.guildOfNothing.general.configuration.validation.dto;

import lombok.Getter;

@Getter
public class FormErrorDto {

	private String field;
	private String error;

	public FormErrorDto(String field, String error) {
		this.field = field;
		this.error = error;
	}

}
