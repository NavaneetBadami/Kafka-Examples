package no.hioa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecutityConfig extends WebSecurityConfigurerAdapter{
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("Navaneet").password("{noop}test123").roles("USER").and()
//		.withUser("TEST").password("{noop}test123").roles("ADMIN");
//		
//	}
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		final User.UserBuilder userBuilder = User.builder().passwordEncoder(encoder::encode);
		UserDetails user = userBuilder
				.username("Navaneet")
				.password("test123")
				.roles("USER")
				.build();

		UserDetails admin = userBuilder
				.username("admin")
				.password("admin")
				.roles("USER","ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user, admin);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.anyRequest()
		/*.permitAll().*/
		.fullyAuthenticated()
		.and().httpBasic();
		http.csrf().disable();
	}
	
}
