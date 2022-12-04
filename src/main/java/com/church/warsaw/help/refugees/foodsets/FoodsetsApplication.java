package com.church.warsaw.help.refugees.foodsets;

import com.church.warsaw.help.refugees.foodsets.entity.RegistrationInfoEntity;
import com.church.warsaw.help.refugees.foodsets.service.CacheStore;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableMongoAuditing
@ConfigurationPropertiesScan("com.church.warsaw.help.refugees.foodsets")
@SpringBootApplication
@AllArgsConstructor
public class FoodsetsApplication {

  private final Environment environment;

  public static void main(String[] args) {
    SpringApplication.run(FoodsetsApplication.class, args);
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("validationMessage");
    return messageSource;
  }

  @Bean
  public CacheStore<RegistrationInfoEntity> registrationInfoCache() {
    return new CacheStore<>(21, TimeUnit.DAYS);
  }

  @Bean
  public Session javaMailSenderImpl() {

    JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();

    String emailHost = environment.getRequiredProperty("email.host");
    String emailPort = environment.getRequiredProperty("email.port");
    String userName = environment.getRequiredProperty("email.username");
    String userPassword = environment.getRequiredProperty("email.password");

    mailSenderImpl.setHost(emailHost);
    mailSenderImpl.setPort(Integer.parseInt(emailPort));
    mailSenderImpl.setUsername(userName);
    mailSenderImpl.setPassword(userPassword);

    Properties properties = System.getProperties();

    properties.put("mail.smtp.host", emailHost);
    properties.put("mail.smtp.port", emailPort);
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.transport.protocol", "smtp");
    properties.put("mail.smtp.auth", "true");

    return Session.getInstance(properties, new javax.mail.Authenticator() {

      @Override
      protected PasswordAuthentication getPasswordAuthentication() {

        return new PasswordAuthentication(userName, userPassword);
      }

    });
  }

  @Bean
  public MimeMessage mimeMessage(Session session) {

    return new MimeMessage(session);
  }

}

