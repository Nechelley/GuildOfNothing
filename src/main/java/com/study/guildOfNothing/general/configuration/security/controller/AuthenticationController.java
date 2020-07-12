package com.study.guildOfNothing.general.configuration.security.controller;

import com.study.guildOfNothing.general.configuration.security.SecurityConfiguration;
import com.study.guildOfNothing.general.configuration.security.dto.TokenOutDto;
import com.study.guildOfNothing.general.configuration.security.dto.UserLoginInDto;
import com.study.guildOfNothing.general.configuration.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenOutDto> authenticate(@RequestBody @Valid UserLoginInDto userLoginInDto) {
		UsernamePasswordAuthenticationToken authenticationToken = userLoginInDto.createAuthenticationToken();

		try {
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			String token = tokenService.createToken(authentication);

			return ResponseEntity.ok(new TokenOutDto(token, SecurityConfiguration.AUTHENTICATION_TYPE));
		} catch (AuthenticationException exception) {
			return ResponseEntity.badRequest().build();
		}
	}

}
