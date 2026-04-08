package proyecto.grupo1.poc_springboot.configuration;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
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

		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(
					"/v3/api-docs/**",
					"/api-docs/**",
					"/swagger-ui/**",
					"/swagger-ui.html"
				).permitAll()
				.requestMatchers("/auth/**").permitAll()
				.anyRequest().authenticated()
			);

		return http.build();
	}

	@Bean
	AuthoritiesConverter realmRolesAuthoritiesConverter() {
		return claims -> {
			var realmAccess = Optional.ofNullable((Map<String, Object>) claims.get("realm_access"));
			var roles = realmAccess.flatMap(map -> Optional.ofNullable((List<String>) map.get("roles")));
			return roles.map(List::stream)
			.orElse(Stream.empty())
			.map(SimpleGrantedAuthority::new)
			.map(GrantedAuthority.class::cast)
			.toList();
		};
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
