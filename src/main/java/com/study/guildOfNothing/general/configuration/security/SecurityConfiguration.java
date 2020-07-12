package com.study.guildOfNothing.general.configuration.security;

import com.study.guildOfNothing.general.configuration.security.service.AuthenticationService;
import com.study.guildOfNothing.general.configuration.security.service.TokenService;
import com.study.guildOfNothing.constant.ProfileEnum;
import com.study.guildOfNothing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRepository userRepository;

	public final static String AUTHENTICATION_TYPE = "Bearer";

	//Login configurations
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(PasswordEncrypter.getEncrypter());
	}

	//autorization configurations
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/authentication").permitAll()

			.antMatchers(HttpMethod.GET, "/user").hasAuthority(ProfileEnum.ADMIN.getName())
			.antMatchers(HttpMethod.GET, "/user/*").hasAuthority(ProfileEnum.ADMIN.getName())
			.antMatchers(HttpMethod.POST, "/user/admin").hasAuthority(ProfileEnum.ADMIN.getName())
			.antMatchers(HttpMethod.POST, "/user").permitAll()
			.antMatchers(HttpMethod.DELETE, "/user").permitAll()

			//for h2
			.antMatchers("/").permitAll()
			//for h2
			.antMatchers("/h2-console/**").permitAll()

			.anyRequest().authenticated()
			.and().csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().addFilterBefore(new AuthenticationByTokenFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class)
			//for h2
			.headers().frameOptions().disable();


	}

	//configuration of static resources like css and images
	@Override
	public void configure(WebSecurity web) throws Exception {

	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

}
