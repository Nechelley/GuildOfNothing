package com.study.guildOfNothing.service.onlyInterface;

import com.study.guildOfNothing.general.configuration.validation.exception.EntityNonExistentForManipulateException;
import com.study.guildOfNothing.general.configuration.validation.exception.FormErrorException;
import com.study.guildOfNothing.general.configuration.validation.exception.TryingManipulateAnotherUserStuffException;
import com.study.guildOfNothing.model.Character;
import com.study.guildOfNothing.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

	Page<User> getUsers(Pageable pageable);

	Optional<User> getUser(Long id);

	User createUser(User user) throws FormErrorException;

	User updateUser(User user) throws TryingManipulateAnotherUserStuffException, EntityNonExistentForManipulateException, FormErrorException;

	void testIfUserTryingManipulateAnotherUser(User user) throws TryingManipulateAnotherUserStuffException;

	void deleteUser(Long id) throws EntityNonExistentForManipulateException, TryingManipulateAnotherUserStuffException;

}
