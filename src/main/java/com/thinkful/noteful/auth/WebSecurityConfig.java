package com.thinkful.noteful.auth;

import static com.thinkful.noteful.auth.SecurityConstants.SIGN_UP_URL;
import static com.thinkful.noteful.auth.SecurityConstants.SIGN_UP_URL_WITH_SLASH;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private UserDetailsService userDetailsService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public WebSecurityConfig(
        UserDetailsService userDetailsService, 
        BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userDetailsService = userDetailsService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .cors()
      .and()
      .csrf()
      .disable()
      .authorizeRequests()
      .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
      .antMatchers(HttpMethod.POST, SIGN_UP_URL_WITH_SLASH).permitAll()
      .anyRequest().authenticated()
      .and()
      .addFilter(new JwtAuthenticationFilter(authenticationManager()))
      .addFilter(new JwtAuthorizationFilter(authenticationManager()))
      .exceptionHandling()
      .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
          .userDetailsService(userDetailsService)
          .passwordEncoder(bCryptPasswordEncoder);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration configuration = new CorsConfiguration();
    configuration = configuration.applyPermitDefaultValues();
    List<String> allowedMethods = new ArrayList<>();
    allowedMethods.add("GET");
    allowedMethods.add("PUT");
    allowedMethods.add("POST");
    allowedMethods.add("DELETE");
    allowedMethods.add("OPTIONS");
    configuration.setAllowedMethods(allowedMethods);
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}