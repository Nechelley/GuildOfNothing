package com.study.guildOfNothing.controller.dto.in;

import com.study.guildOfNothing.general.dtoGroup.OnCreate;
import com.study.guildOfNothing.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class UserInDto {

	@NotEmpty
	@Length(min = 5, max = 100)
	private String name;
	@NotEmpty
	@Length(min = 10, max = 500)
	private String password;
	@NotEmpty(groups = OnCreate.class)
	@Length(min = 10, max = 100, groups = OnCreate.class)
	@Email
	private String email;

	public User createUser() {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		return user;
	}

}
