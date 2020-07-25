package com.study.guildOfNothing.service;

import com.study.guildOfNothing.general.configuration.security.PasswordEncrypter;
import com.study.guildOfNothing.general.configuration.validation.exception.EntityNonExistentForManipulateException;
import com.study.guildOfNothing.general.configuration.validation.exception.FormErrorException;
import com.study.guildOfNothing.general.configuration.validation.exception.TryingManipulateAnotherUserStuffException;
import com.study.guildOfNothing.model.User;
import com.study.guildOfNothing.repository.UserRepository;
import com.study.guildOfNothing.service.onlyInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Page<User> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Optional<User> getUser(Long id) {
		return userRepository.findById(id);
	}

	@Override
	@Transactional
	public User createUser(User user) throws FormErrorException {
		user.setPassword(PasswordEncrypter.getEncrypter().encode(user.getPassword()));

		testIfUserEmailAlreadyRegistered(user);

		return userRepository.save(user);
	}

	private void testIfUserEmailAlreadyRegistered(User user) throws FormErrorException {
		Optional<User> userInDatabase = userRepository.findByEmail(user.getEmail());

		boolean emailAlreadyRegistered = userInDatabase.isPresent();
		if (emailAlreadyRegistered)
			throw new FormErrorException("email", "email already registered");
	}

	@Override
	@Transactional
	public User updateUser(User user) throws TryingManipulateAnotherUserStuffException, EntityNonExistentForManipulateException {
		Optional<User> userInDatabase = userRepository.findById(user.getId());
		if (!userInDatabase.isPresent())
			throw new EntityNonExistentForManipulateException();

		testIfUserTryingManipulateAnotherUser(user);

		User userToUpdate = userInDatabase.get();

		userToUpdate.setName(user.getName());
		userToUpdate.setPassword(PasswordEncrypter.getEncrypter().encode(user.getPassword()));

		return userRepository.save(userToUpdate);
	}

	@Override
	public void testIfUserTryingManipulateAnotherUser(User user) throws TryingManipulateAnotherUserStuffException {
		User userLogged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean userTryingUpdateAnotherUser = !userLogged.getId().equals(user.getId());
		if (userTryingUpdateAnotherUser)
			throw new TryingManipulateAnotherUserStuffException();
	}

	@Override
	public void deleteUser(Long id) throws EntityNonExistentForManipulateException, TryingManipulateAnotherUserStuffException {
		Optional<User> userInDatabase = userRepository.findById(id);
		if (!userInDatabase.isPresent())
			throw new EntityNonExistentForManipulateException();

		testIfUserTryingManipulateAnotherUser(userInDatabase.get());

		userRepository.deleteById(id);
	}

}
