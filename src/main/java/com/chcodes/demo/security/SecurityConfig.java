package com.chcodes.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.chcodes.demo.filter.CustomAuthentificationFilter;
import com.chcodes.demo.filter.CustomAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService UserDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(UserDetailsService).passwordEncoder(bCryptPasswordEncoder);
		super.configure(auth);
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthentificationFilter customAuthentificationFilter = new CustomAuthentificationFilter(
				authenticationManagerBean());
		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
		// to change the default url /login to /api/login
		customAuthentificationFilter.setFilterProcessesUrl("/api/login");
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		/*http.authorizeRequests().antMatchers("/api/users/**", "/api/login/**", "/api/token/refresh/**",
				"/admin/**", "/user/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/products/**").hasAuthority("ROLE_USER");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/addProduct/**",
				"/api/role/save/**", "/api/addProducts/**").hasAuthority("ROLE_ADMIN");*/
		http.authorizeRequests().antMatchers("/api/**").permitAll();
		http.authorizeRequests().antMatchers("/v3/api-docs/**").permitAll()
        .antMatchers("/configuration/ui").permitAll()
        .antMatchers("/swagger-resources/**").permitAll()
        .antMatchers("/configuration/security").permitAll()
        .antMatchers("/swagger-ui.html").permitAll()
        .antMatchers("/swagger-ui/**").permitAll();
		http.formLogin()
		.loginPage("/login")
		.permitAll();
		
		//http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(customAuthentificationFilter);

		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
