package com.atri.MeetupBackend;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfigurationBasicAuth extends WebSecurityConfigurerAdapter{
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.cors().and().csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS,"/**").hasIpAddress("192.168.1.9");
        http.headers().frameOptions().disable();
    }
    

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
		/*
		 * final CorsConfiguration configuration = new CorsConfiguration();
		 * configuration.setAllowedOrigins(Arrays.asList("*"));
		 * configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT",
		 * "DELETE", "PATCH")); // setAllowCredentials(true) is important, otherwise: //
		 * The value of the 'Access-Control-Allow-Origin' header in the response must
		 * not be the wildcard '*' when the request's credentials mode is 'include'.
		 * configuration.setAllowCredentials(true); // setAllowedHeaders is important!
		 * Without it, OPTIONS preflight request // will fail with 403 Invalid CORS
		 * request configuration.setAllowedHeaders(Arrays.asList("Authorization",
		 * "Cache-Control", "Content-Type"));
		 */
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
    
    
}