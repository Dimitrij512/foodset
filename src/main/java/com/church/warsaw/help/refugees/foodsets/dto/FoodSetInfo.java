package com.church.warsaw.help.refugees.foodsets.dto;

import static lombok.AccessLevel.PRIVATE;

import com.church.warsaw.help.refugees.foodsets.TypeSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.joda.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class FoodSetInfo {

  String refugeeId;

  LocalDate receiveDate;

  String receiveTime;

  TypeSet typeSet;

  boolean isReceive;

  public static FoodSetInfo of(String refugeeId, RegistrationInfo registrationInfo) {

    return FoodSetInfo.builder()
        .refugeeId(refugeeId)
        .receiveDate(LocalDate.parse(registrationInfo.getDate()))
        .receiveTime(registrationInfo.getTime())
        .typeSet(TypeSet.valueOf(registrationInfo.getTypeSet()))
        .build();
  }
}
