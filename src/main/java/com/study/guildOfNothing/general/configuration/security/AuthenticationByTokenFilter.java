package com.study.guildOfNothing.general.configuration.security;

import com.study.guildOfNothing.general.configuration.security.controller.AuthenticationController;
import com.study.guildOfNothing.general.configuration.security.service.TokenService;
import com.study.guildOfNothing.model.User;
import com.study.guildOfNothing.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationByTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;

	private UserRepository userRepository;

	public AuthenticationByTokenFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = recoverToken(request);
		boolean isValid = tokenService.isValidToken(token);
		if (isValid)
			authenticateUser(token);

		filterChain.doFilter(request, response);
	}

	private String recoverToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty() || !token.startsWith(SecurityConfiguration.AUTHENTICATION_TYPE + " "))
			return null;

		return token.substring(7);
	}

	private void authenticateUser(String token) {
		Long userId = tokenService.getUserId(token);
		User user = userRepository.findById(userId).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
