package com.church.warsaw.help.refugees.foodsets.config;

import java.util.Map;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Configuration
@Validated
@ConfigurationProperties(prefix = "food-set-form")
public class FoodSetConfiguration {


  @NotEmpty(message = "Список потоків видачі допомоги не вказаний")
  Map<String, Integer> mapStreamsWithMaxCount;

}
