package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

abstract class AbstractPermitAllTest {

  @Resource
  private TestRestTemplate restTemplate;

  @Test
  void methodNotAllowed() {
    assertThat(restTemplate
        .postForEntity("/api/test", null, Void.class))
        .hasFieldOrPropertyWithValue("statusCode", HttpStatus.METHOD_NOT_ALLOWED);
  }

  @Test
  void noContent() {
    assertThat(restTemplate
        .getForEntity("/api/test", null, Void.class))
        .hasFieldOrPropertyWithValue("statusCode", HttpStatus.NO_CONTENT);
  }

  @Test
  void notFound() {
    assertThat(restTemplate
        .getForEntity("/api/unknown-endpoint", null, Void.class))
        .hasFieldOrPropertyWithValue("statusCode", HttpStatus.NOT_FOUND);
  }

  @SpringBootApplication(proxyBeanMethods = false)
  @RestController
  static class TestApplication {

    @GetMapping("/api/test")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void test() {
    }
  }
}

