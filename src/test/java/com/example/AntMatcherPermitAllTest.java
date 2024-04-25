package com.example;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import com.example.AbstractPermitAllTest.TestApplication;
import com.example.AntMatcherPermitAllTest.SecurityConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootTest(
    classes = { TestApplication.class, SecurityConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AntMatcherPermitAllTest extends AbstractPermitAllTest {

  @TestConfiguration
  static class SecurityConfiguration {

    @Bean
    public SecurityFilterChain basicWebSecurityFilterChain(HttpSecurity http) throws Exception {
      return http
          .authorizeHttpRequests(
              auth -> auth.requestMatchers(antMatcher("/api/**"), antMatcher("/error"))
                  .permitAll()
          )
          .httpBasic(basic -> basic.realmName("test"))
          .csrf(CsrfConfigurer::disable)
          .sessionManagement(sessionConfigurer -> sessionConfigurer
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          )
          .build();
    }
  }
}

