package proyecto.grupo1.poc_springboot.configuration;

import java.util.Collection;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	interface AuthoritiesConverter extends Converter<Map<String, Object>, Collection<GrantedAuthority>> {}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	@Profile("dev")
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
		Converter<Jwt, AbstractAuthenticationToken> authenticationConverter
	) throws Exception {
		http.oauth2ResourceServer(resourceServer -> {
			resourceServer.jwt(jwtDecoder -> {
				jwtDecoder.jwtAuthenticationConverter(authenticationConverter);
			});
		});

		http.sessionManagement(sessions -> {
			sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}).csrf(csrf -> {
			csrf.disable();
		});

		http.authorizeHttpRequests(requests -> {
			requests.requestMatchers("/me").authenticated();
			requests.anyRequest().denyAll();
		});

		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(
					"/v3/api-docs*/**",
					"/api-docs/**",
					"/swagger-ui/**",
					"/swagger-ui.html"
				).permitAll()
				.anyRequest().authenticated()
			);

		return http.build();
	}

	@Bean
	JwtAuthenticationConverter authenticationConverter(AuthoritiesConverter authoritiesConverter) {
		var authenticationConverter = new JwtAuthenticationConverter();
		authenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			return authoritiesConverter.convert(jwt.getClaims());
		});
		return authenticationConverter;
	}
}
