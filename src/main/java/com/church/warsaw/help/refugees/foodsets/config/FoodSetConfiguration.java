package com.church.warsaw.help.refugees.foodsets.config;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Configuration
@Validated
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "food-set-form")
public class FoodSetConfiguration {

  Map<String, Integer> mondayConfiguration;
  Map<String, Integer> tuesdayConfiguration;
  Map<String, Integer> wednesdayConfiguration;
  Map<String, Integer> thursdayConfiguration;
  Map<String, Integer> fridayConfiguration;


}
