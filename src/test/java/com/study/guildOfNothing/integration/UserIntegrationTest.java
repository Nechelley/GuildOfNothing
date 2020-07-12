package com.study.guildOfNothing.integration;

import com.study.guildOfNothing.controller.dto.in.UserInDto;
import com.study.guildOfNothing.controller.dto.out.UserOutDto;
import com.study.guildOfNothing.general.configuration.security.SecurityConfiguration;
import com.study.guildOfNothing.general.configuration.security.dto.TokenOutDto;
import com.study.guildOfNothing.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc
class UserIntegrationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private int port;

	private static User userInDatabase;
	private static User temporaryUser;

	private static HttpHeaders userInDatabaseHeaders;
	private static HttpHeaders temporaryUserHeaders;
	private static HttpHeaders headersNotAuthorized;

	private static Long userIdNotExistent;
	private static int randomNumberForUpdate;

	@BeforeAll
	static void setUp() {
		initializeUserInDatabase();
		initializeTemporaryUser();

		userInDatabaseHeaders = new HttpHeaders();
		temporaryUserHeaders = new HttpHeaders();
		headersNotAuthorized = new HttpHeaders();

		userIdNotExistent = 5000L;
		Random random = new Random();
		randomNumberForUpdate = random.nextInt(42);
	}

	private static void initializeUserInDatabase() {
		userInDatabase = new User();
		userInDatabase.setId(3L);
		userInDatabase.setName("Basic name");
		userInDatabase.setEmail("basicTest@basicTest.basicTest");
		userInDatabase.setPassword("123");
	}

	private static void initializeTemporaryUser() {
		temporaryUser = new User();
		temporaryUser.setName("Temporary User Name");
		temporaryUser.setEmail("temporaryUser@temporaryUser.temporaryUser");
		temporaryUser.setPassword("1234567890");
	}

	@Test
	@Order(1)
	void createUserFailBecausePasswordNotHaveSufficientLength() {
		UserInDto user = new UserInDto(temporaryUser);
		String passwordWithInsufficientLength = "12345";
		user.setPassword(passwordWithInsufficientLength);

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user"), HttpMethod.POST, entity, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Order(2)
	void createUserFailBecauseEmailAlreadyExists() {
		UserInDto user = new UserInDto(userInDatabase);

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user"), HttpMethod.POST, entity, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Order(3)
	void createUserSuccess() {
		UserInDto user = new UserInDto(temporaryUser);

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user);

		ResponseEntity<UserOutDto> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user"), HttpMethod.POST, entity, UserOutDto.class);

		UserOutDto userCreated = responseEntity.getBody();

		assertNotNull(userCreated);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(temporaryUser.getEmail(), userCreated.getEmail());
		assertEquals(temporaryUser.getName(), userCreated.getName());

		//setting id for the nexts tests
		temporaryUser.setId(userCreated.getId());
	}

	@Test
	@Order(4)
	void authenticationUserFailBecauseIncorrectPassword() {
		UserInDto user = new UserInDto(temporaryUser);
		String incorrectPassword = "incorrect";
		user.setPassword(incorrectPassword);

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/authentication"), HttpMethod.POST, entity, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Order(5)
	void authenticationUserSuccess() {
		UserInDto user = new UserInDto(temporaryUser);

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user);

		ResponseEntity<TokenOutDto> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/authentication"), HttpMethod.POST, entity, TokenOutDto.class);

		TokenOutDto token = responseEntity.getBody();

		assertNotNull(token);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(SecurityConfiguration.AUTHENTICATION_TYPE, token.getType());
		//<DOUBT>how to test token? Using the method of service? Or Do Not Test?

		//setting headers for the nexts tests
		temporaryUserHeaders.set("Authorization", SecurityConfiguration.AUTHENTICATION_TYPE + " " + token.getToken());
	}

	@Test
	@Order(6)
	void updateUserFailBecauseUserNotExists() {
		temporaryUser.setName(temporaryUser.getName() + randomNumberForUpdate);

		UserInDto user = new UserInDto(temporaryUser);

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user, temporaryUserHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+ userIdNotExistent), HttpMethod.PUT, entity, String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	@Order(7)
	void updateUserFailBecauseTryingUpdateAnotherUser() {
		UserInDto user = new UserInDto(temporaryUser);

		settingHeadersForUserInDatabase();
		final HttpEntity<UserInDto> entity = new HttpEntity<>(user, userInDatabaseHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+ temporaryUser.getId()), HttpMethod.PUT, entity, String.class);

		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	}

	@Test
	@Order(8)
	void updateUserFailBecauseNotAuthorizedHeader() {
		UserInDto user = new UserInDto(temporaryUser);

		settingHeadersForUserInDatabase();
		final HttpEntity<UserInDto> entity = new HttpEntity<>(user, headersNotAuthorized);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+ temporaryUser.getId()), HttpMethod.PUT, entity, String.class);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	@Order(9)
	void updateUserFailBecausePasswordNotHaveSufficientLength() {
		UserInDto user = new UserInDto(temporaryUser);
		String passwordWithInsufficientLength = "12345";
		user.setPassword(passwordWithInsufficientLength);

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user, temporaryUserHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+ temporaryUser.getId()), HttpMethod.PUT, entity, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Order(10)
	void updateUserSuccess() {
		UserInDto user = new UserInDto(temporaryUser);

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user, temporaryUserHeaders);

		ResponseEntity<UserOutDto> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+ temporaryUser.getId()), HttpMethod.PUT, entity, UserOutDto.class);

		UserOutDto userCreated = responseEntity.getBody();

		assertNotNull(userCreated);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(temporaryUser.getEmail(), userCreated.getEmail());
		assertEquals(temporaryUser.getName(), userCreated.getName());
	}

	@Test
	@Order(11)
	void deleteUserFailBecauseTryingDeleteAnotherUser() {
		final HttpEntity<UserInDto> entity = new HttpEntity<>(userInDatabaseHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+ temporaryUser.getId()), HttpMethod.DELETE, entity, String.class);

		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	}

	@Test
	@Order(12)
	void deleteUserFailBecauseNotAuthorized() {
		final HttpEntity<UserInDto> entity = new HttpEntity<>(headersNotAuthorized);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+ temporaryUser.getId()), HttpMethod.DELETE, entity, String.class);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	@Order(13)
	void deleteUserFailBecauseUserNotExists() {
		final HttpEntity<UserInDto> entity = new HttpEntity<>(temporaryUserHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+ userIdNotExistent), HttpMethod.DELETE, entity, String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	@Order(14)
	void deleteUserSuccess() {
		final HttpEntity<UserInDto> entity = new HttpEntity<>(temporaryUserHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+ temporaryUser.getId()), HttpMethod.DELETE, entity, String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	private void settingHeadersForUserInDatabase() {
		final HttpEntity<User> entity = new HttpEntity<>(userInDatabase);

		ResponseEntity<TokenOutDto> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/authentication"), HttpMethod.POST, entity, TokenOutDto.class);

		TokenOutDto token = responseEntity.getBody();

		userInDatabaseHeaders.set("Authorization", SecurityConfiguration.AUTHENTICATION_TYPE+" "+token.getToken());
	}

	private String getUrlForEndpoint(String endpoint) {
		return "http://localhost:"+port+endpoint;
	}

}
