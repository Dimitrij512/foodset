package com.church.warsaw.help.refugees.foodsets.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
@Getter
@Setter
@Configuration
@Validated
@ConfigurationProperties(prefix = "admin-user")
public class UserConfiguration {

  String username;

  String password;

  String role;
}
