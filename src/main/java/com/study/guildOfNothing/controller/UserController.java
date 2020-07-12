package com.study.guildOfNothing.controller;

import com.study.guildOfNothing.constant.ProfileEnum;
import com.study.guildOfNothing.controller.dto.out.UserOutDto;
import com.study.guildOfNothing.general.configuration.validation.exception.EntityNonExistentForManipulateException;
import com.study.guildOfNothing.general.configuration.validation.exception.FormErrorException;
import com.study.guildOfNothing.general.configuration.validation.exception.TryingManipulateAnotherUserStuffException;
import com.study.guildOfNothing.model.Profile;
import com.study.guildOfNothing.model.User;
import com.study.guildOfNothing.controller.dto.in.UserInDto;
import com.study.guildOfNothing.general.dtoGroup.OnCreate;
import com.study.guildOfNothing.service.onlyInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public Page<UserOutDto> getAll(@PageableDefault(sort = "email", direction = Direction.ASC) Pageable pageable) {
		return UserOutDto.createDtoFromUsersList(userService.getUsers(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserOutDto> findOne(@PathVariable Long id) {
		Optional<User> user = userService.getUser(id);

		if (!user.isPresent())
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(new UserOutDto(user.get()));
	}

	@PostMapping
	@Validated(OnCreate.class)
	public ResponseEntity<UserOutDto> createBasic(@RequestBody @Valid UserInDto userInDto, UriComponentsBuilder uriBuilder) throws FormErrorException {
		return createUser(userInDto, uriBuilder, ProfileEnum.BASIC);
	}

	@PostMapping("/admin")
	@Validated(OnCreate.class)
	public ResponseEntity<UserOutDto> createAdmin(@RequestBody @Valid UserInDto userInDto, UriComponentsBuilder uriBuilder) throws FormErrorException {
		return createUser(userInDto, uriBuilder, ProfileEnum.ADMIN);
	}

	private ResponseEntity<UserOutDto> createUser(UserInDto userInDto, UriComponentsBuilder uriBuilder, ProfileEnum profileEnum) throws FormErrorException {
		User user = userInDto.createUser();

		List<Profile> profiles = new ArrayList<>();
		profiles.add(new Profile(profileEnum.getId(), profileEnum.getName()));
		user.setProfiles(profiles);

		User userCreated = userService.createUser(user);

		URI uri = uriBuilder.path("/user/{id}").buildAndExpand(userCreated.getId()).toUri();
		return ResponseEntity.created(uri).body(new UserOutDto(userCreated));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserOutDto> update(@PathVariable Long id, @RequestBody @Valid UserInDto userInDto) throws EntityNonExistentForManipulateException, TryingManipulateAnotherUserStuffException, FormErrorException {
		User user = userInDto.createUser();
		user.setId(id);
		User userUpdated = userService.updateUser(user);

		return ResponseEntity.ok(new UserOutDto(userUpdated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) throws TryingManipulateAnotherUserStuffException, EntityNonExistentForManipulateException {
		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}

}
