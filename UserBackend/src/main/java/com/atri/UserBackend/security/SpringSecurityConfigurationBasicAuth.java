package com.atri.UserBackend.security;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.atri.UserBackend.service.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfigurationBasicAuth extends WebSecurityConfigurerAdapter{
    private UserService userService;
    private Environment env;
    private BCryptPasswordEncoder pwdEnc;
	
    public SpringSecurityConfigurationBasicAuth(UserService us, Environment env, BCryptPasswordEncoder pwdEnc) {
    	this.userService = us;
    	this.env = env;
    	this.pwdEnc = pwdEnc;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.cors().and().csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS,"/**").hasIpAddress(env.getProperty("gateway.ip"))
                .and()
                .addFilter(getAuthFilter());
        http.headers().frameOptions().disable();
    }
    
    private AuthenticationFilter getAuthFilter() throws Exception {
    	AuthenticationFilter authFilter = new AuthenticationFilter(userService, env, authenticationManager());
    	try {
			authFilter.setAuthenticationManager(authenticationManager());
			authFilter.setFilterProcessesUrl(env.getProperty("login.auth.url"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return authFilter;
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
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userService).passwordEncoder(pwdEnc);
    }
    
}