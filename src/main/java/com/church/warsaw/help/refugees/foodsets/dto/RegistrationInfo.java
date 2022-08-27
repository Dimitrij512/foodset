package com.church.warsaw.help.refugees.foodsets.dto;

import static lombok.AccessLevel.PRIVATE;

import com.church.warsaw.help.refugees.foodsets.validator.CheckDateFormat;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class RegistrationInfo {

  String id;

  @CheckDateFormat(pattern = "yyyy-mm-dd", message = "не вірна дата")
  String receiveDate;

  @Pattern(regexp = "^(0?[1-9]|1[0-9]):[0-5][0-9]$", message = "не вірний формат потоку")
  String stream;

  @NotBlank(message = "ім'я не може бути пустим")
  String name;

  @NotBlank(message = "фамілія не може бути пустим")
  String surname;

  @Pattern(regexp = "^\\d{8,13}$", message = "телефон вказано не вірно")
  String phoneNumber;

  @Min(value = 0, message = "кількість дітей не може бути менше нуля")
  @Max(value = 10, message = "кількість дітей не може бути більше 10")
  int kidsCount;

  @NotBlank(message = "тип допомоги не може бути пустим")
  String typeSet;

  @Builder.Default
  String receive = "Ні";

  String comment;

}
