package com.church.warsaw.help.refugees.foodsets.entity;

import com.church.warsaw.help.refugees.foodsets.TypeSet;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDate;

@Getter
@Setter
public class FoodSetInfoEntity extends BaseEntity {

  String refugeeId;

  LocalDate receiveDate;

  String receiveTime;

  TypeSet typeSet;

  boolean isReceive;
}
