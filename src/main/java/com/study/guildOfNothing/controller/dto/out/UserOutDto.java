package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
public class UserOutDto {

	private Long id;
	private String name;
	private String email;

	public UserOutDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
	}

	public static Page<UserOutDto> createDtoFromUsersList(Page<User> users) {
		return users.map(UserOutDto::new);
	}

}
