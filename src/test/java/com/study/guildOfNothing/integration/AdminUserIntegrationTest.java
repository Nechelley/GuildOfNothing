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
class AdminUserIntegrationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private int port;

	private static User adminUserInDatabase;
	private static User temporaryAdminUser;

	private static HttpHeaders adminUserInDatabaseHeaders;
	private static HttpHeaders temporaryAdminUserHeaders;
	private static HttpHeaders headersNotAuthorized;

	private static Long adminIdNotExistent;
	private static int randomNumberForUpdate;

	@BeforeAll
	static void setUp() {
		initializeAdminUserInDatabase();
		initializeTemporaryAdminUser();

		adminUserInDatabaseHeaders = new HttpHeaders();
		temporaryAdminUserHeaders = new HttpHeaders();
		headersNotAuthorized = new HttpHeaders();

		adminIdNotExistent = 5000L;
		Random random = new Random();
		randomNumberForUpdate = random.nextInt(42);
	}

	private static void initializeAdminUserInDatabase() {
		adminUserInDatabase = new User();
		adminUserInDatabase.setId(3L);
		adminUserInDatabase.setName("Admin Name");
		adminUserInDatabase.setEmail("adminTest@adminTest.adminTest");
		adminUserInDatabase.setPassword("123");
	}

	private static void initializeTemporaryAdminUser() {
		temporaryAdminUser = new User();
		temporaryAdminUser.setName("Temporary Admin Name");
		temporaryAdminUser.setEmail("temporaryAdmin@temporaryAdmin.temporaryAdmin");
		temporaryAdminUser.setPassword("1234567890");
	}

	@Test
	@Order(1)
	void authenticationAdminUserFailBecauseIncorrectPassword() {
		UserInDto user = new UserInDto();
		user.setEmail(adminUserInDatabase.getEmail());
		String incorrectPassword = "incorrect";
		user.setPassword(incorrectPassword);

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/authentication"), HttpMethod.POST, entity, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Order(2)
	void authenticationAdminUserSuccess() {
		UserInDto user = new UserInDto();
		user.setEmail(adminUserInDatabase.getEmail());
		user.setPassword(adminUserInDatabase.getPassword());

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user);

		ResponseEntity<TokenOutDto> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/authentication"), HttpMethod.POST, entity, TokenOutDto.class);

		TokenOutDto token = responseEntity.getBody();

		assertNotNull(token);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(SecurityConfiguration.AUTHENTICATION_TYPE, token.getType());
		//<DOUBT>how to test token? Using the method of service? Or Do Not Test?

		//setting headers for the nexts tests
		adminUserInDatabaseHeaders.set("Authorization", SecurityConfiguration.AUTHENTICATION_TYPE + " " + token.getToken());
	}

	@Test
	@Order(3)
	void createAdminUserFailBecausePasswordNotHaveSufficientLength() {
		UserInDto user = new UserInDto();
		user.setName(temporaryAdminUser.getName());
		user.setEmail(temporaryAdminUser.getEmail());
		String passwordWithInsufficientLength = "12345";
		user.setPassword(passwordWithInsufficientLength);

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user, adminUserInDatabaseHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/admin"), HttpMethod.POST, entity, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Order(4)
	void createAdminUserFailBecauseEmailAlreadyExists() {
		UserInDto user = new UserInDto();
		user.setName(adminUserInDatabase.getName());
		user.setEmail(adminUserInDatabase.getEmail());
		user.setPassword(adminUserInDatabase.getPassword());

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user, adminUserInDatabaseHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/admin"), HttpMethod.POST, entity, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Order(5)
	void createAdminUserFailBecauseNotAdminHeaders() {
		UserInDto user = new UserInDto();
		user.setName(adminUserInDatabase.getName());
		user.setEmail(adminUserInDatabase.getEmail());
		user.setPassword(adminUserInDatabase.getPassword());

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user, headersNotAuthorized);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/admin"), HttpMethod.POST, entity, String.class);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	@Order(6)
	void createAdminUserSuccess() {
		UserInDto user = new UserInDto();
		user.setName(temporaryAdminUser.getName());
		user.setEmail(temporaryAdminUser.getEmail());
		user.setPassword(temporaryAdminUser.getPassword());

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user, adminUserInDatabaseHeaders);

		ResponseEntity<UserOutDto> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/admin"), HttpMethod.POST, entity, UserOutDto.class);

		UserOutDto userCreated = responseEntity.getBody();

		assertNotNull(userCreated);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(temporaryAdminUser.getEmail(), userCreated.getEmail());
		assertEquals(temporaryAdminUser.getName(), userCreated.getName());

		//setting id for the nexts tests
		temporaryAdminUser.setId(userCreated.getId());
	}

	@Test
	@Order(7)
	void getAdminUserFailBecauseAdminNotExists() {
		final HttpEntity<UserInDto> entity = new HttpEntity<>(adminUserInDatabaseHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+adminIdNotExistent), HttpMethod.GET, entity, String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	@Order(8)
	void getAdminUserFailBecauseNotAuthorized() {
		HttpHeaders headersNotAuthorized = new HttpHeaders();
		final HttpEntity<UserInDto> entity = new HttpEntity<>(headersNotAuthorized);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+temporaryAdminUser.getId()), HttpMethod.GET, entity, String.class);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	@Order(9)
	void getAdminUserSuccess() {
		final HttpEntity<UserInDto> entity = new HttpEntity<>(adminUserInDatabaseHeaders);

		ResponseEntity<UserOutDto> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+temporaryAdminUser.getId()), HttpMethod.GET, entity, UserOutDto.class);

		UserOutDto userSearched = responseEntity.getBody();

		assertNotNull(userSearched);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(userSearched.getId(), temporaryAdminUser.getId());
		assertEquals(userSearched.getEmail(), temporaryAdminUser.getEmail());
		assertEquals(userSearched.getName(), temporaryAdminUser.getName());
	}

	@Test
	@Order(10)
	void updateAdminUserFailBecauseUserNotExists() {
		temporaryAdminUser.setName(temporaryAdminUser.getName() + randomNumberForUpdate);

		UserInDto user = new UserInDto();
		user.setName(temporaryAdminUser.getName());
		user.setPassword(temporaryAdminUser.getPassword());

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user, adminUserInDatabaseHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+adminIdNotExistent), HttpMethod.PUT, entity, String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	@Order(11)
	void updateAdminUserFailBecauseTryingUpdateAnotherUser() {
		UserInDto user = new UserInDto();
		user.setName(temporaryAdminUser.getName());
		user.setPassword(temporaryAdminUser.getPassword());

		final HttpEntity<UserInDto> entity = new HttpEntity<>(user, adminUserInDatabaseHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+temporaryAdminUser.getId()), HttpMethod.PUT, entity, String.class);

		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	}

	@Test
	@Order(12)
	void updateAdminUserFailBecausePasswordNotHaveSufficientLength() {
		UserInDto user = new UserInDto();
		user.setName(temporaryAdminUser.getName());
		String passwordWithInsufficientLength = "12345";
		user.setPassword(passwordWithInsufficientLength);

		settingHeadersForTemporaryAdminUser();
		final HttpEntity<UserInDto> entity = new HttpEntity<>(user, temporaryAdminUserHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+temporaryAdminUser.getId()), HttpMethod.PUT, entity, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Order(13)
	void updateAdminUserSuccess() {
		UserInDto user = new UserInDto();
		user.setName(temporaryAdminUser.getName());
		user.setPassword(temporaryAdminUser.getPassword());

		settingHeadersForTemporaryAdminUser();
		final HttpEntity<UserInDto> entity = new HttpEntity<>(user, temporaryAdminUserHeaders);

		ResponseEntity<UserOutDto> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+temporaryAdminUser.getId()), HttpMethod.PUT, entity, UserOutDto.class);

		UserOutDto userCreated = responseEntity.getBody();

		assertNotNull(userCreated);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(temporaryAdminUser.getEmail(), userCreated.getEmail());
		assertEquals(temporaryAdminUser.getName(), userCreated.getName());
	}

	@Test
	@Order(14)
	void deleteAdminUserFailBecauseNotAuthorized() {
		HttpHeaders headersNotAuthorized = new HttpHeaders();
		final HttpEntity<UserInDto> entity = new HttpEntity<>(headersNotAuthorized);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+temporaryAdminUser.getId()), HttpMethod.DELETE, entity, String.class);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	@Order(15)
	void deleteAdminUserFailBecauseAdminNotExists() {
		final HttpEntity<UserInDto> entity = new HttpEntity<>(temporaryAdminUserHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+adminIdNotExistent), HttpMethod.DELETE, entity, String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	@Order(16)
	void deleteAdminUserSuccess() {
		final HttpEntity<UserInDto> entity = new HttpEntity<>(temporaryAdminUserHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/user/"+temporaryAdminUser.getId()), HttpMethod.DELETE, entity, String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	private void settingHeadersForTemporaryAdminUser() {
		final HttpEntity<User> entity = new HttpEntity<>(temporaryAdminUser);

		ResponseEntity<TokenOutDto> responseEntity = testRestTemplate
				.exchange(getUrlForEndpoint("/authentication"), HttpMethod.POST, entity, TokenOutDto.class);

		TokenOutDto token = responseEntity.getBody();

		temporaryAdminUserHeaders.set("Authorization", SecurityConfiguration.AUTHENTICATION_TYPE+" "+token.getToken());
	}

	private String getUrlForEndpoint(String endpoint) {
		return "http://localhost:"+port+endpoint;
	}

}
