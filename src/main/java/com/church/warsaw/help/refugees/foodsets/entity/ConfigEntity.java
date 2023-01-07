package com.church.warsaw.help.refugees.foodsets.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ConfigEntity extends BaseEntity {

    List<LocalDate> availableDates = new ArrayList<>();
}
