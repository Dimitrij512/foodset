package com.church.warsaw.help.refugees.foodsets.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Refugee {

  String id;

  String name;

  String surname;

  String phone;

  int kidsCount;


  public static Refugee of(RegistrationInfo foodSetForm) {
    return Refugee.builder()
        .name(foodSetForm.getName())
        .surname(foodSetForm.getSurname())
        .phone(foodSetForm.getPhoneNumber())
        .kidsCount(foodSetForm.getKidsCount())
        .build();
  }

}
