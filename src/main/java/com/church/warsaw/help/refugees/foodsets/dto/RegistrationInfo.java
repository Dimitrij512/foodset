package com.church.warsaw.help.refugees.foodsets.dto;

import static lombok.AccessLevel.PRIVATE;

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

  String date;

  String time;

  String name;

  String surname;

  String phoneNumber;

  int kidsCount;

  String typeSet;

  String isReceive;

  public static RegistrationInfo of(Refugee refugee, FoodSetInfo foodSetInfo) {

    return RegistrationInfo.builder()
        .date(foodSetInfo.getReceiveDate().toString())
        .time(foodSetInfo.getReceiveTime())
        .name(refugee.getName())
        .surname(refugee.getSurname())
        .phoneNumber(refugee.getPhone())
        .kidsCount(refugee.getKidsCount())
        .typeSet(foodSetInfo.getTypeSet().getTypeName())
        .isReceive(foodSetInfo.isReceive() ? "Так" : "Ні")
        .build();
  }


}
