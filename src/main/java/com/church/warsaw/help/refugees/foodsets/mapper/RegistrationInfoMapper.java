package com.church.warsaw.help.refugees.foodsets.mapper;

import com.church.warsaw.help.refugees.foodsets.CategoryOfAssistance;
import com.church.warsaw.help.refugees.foodsets.TypeSet;
import com.church.warsaw.help.refugees.foodsets.dto.RegistrationInfo;
import com.church.warsaw.help.refugees.foodsets.entity.RegistrationInfoEntity;
import java.time.LocalDate;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegistrationInfoMapper {

  RegistrationInfoMapper INSTANCE = Mappers.getMapper(RegistrationInfoMapper.class);

  @Mapping(source = "receive", target = "receive", qualifiedByName = "receiveToString")
  @Mapping(source = "receiveDate", target = "receiveDate", qualifiedByName = "receiveDateToString")
  @Mapping(source = "typeSet", target = "typeSet", qualifiedByName = "typeSetString")
  @Mapping(source = "categoriesAssistance", target = "categoriesAssistance", qualifiedByName = "categoriesAssistanceString")
  @Mapping(source = "phoneNumberMessenger", target = "phoneNumberMessenger", qualifiedByName = "phoneNumberMessengerNonNull")
  RegistrationInfo toDto(RegistrationInfoEntity registrationInfoEntity);

  @Mapping(source = "receive", target = "receive", qualifiedByName = "receiveToBoolean")
  @Mapping(source = "receiveDate", target = "receiveDate", qualifiedByName = "receiveDateLocalDate")
  @Mapping(source = "typeSet", target = "typeSet", qualifiedByName = "typeSetEnum")
  @Mapping(source = "categoriesAssistance", target = "categoriesAssistance", qualifiedByName = "categoriesAssistanceEnum")
  @Mapping(source = "phoneNumberMessenger", target = "phoneNumberMessenger", qualifiedByName = "phoneNumberMessengerNonNull")
  RegistrationInfoEntity toEntity(RegistrationInfo registrationInfo);

  @Named("typeSetEnum")
  static TypeSet getTypeSet(String typeSet) {
    return TypeSet.valueOf(typeSet);
  }

  @Named("phoneNumberMessengerNonNull")
  static String getPhoneNumberMessenger(String phoneNumberMessenger) {
    return phoneNumberMessenger == null ? StringUtils.EMPTY : phoneNumberMessenger;
  }

  @Named("categoriesAssistanceEnum")
  static CategoryOfAssistance getAssistanceCategory(String assistanceCategory) {

    return assistanceCategory == null ? null : CategoryOfAssistance.valueOf(assistanceCategory);
  }

  @Named("typeSetString")
  static String getTypeSetString(TypeSet typeSet) {
    return typeSet.getTypeName();
  }

  @Named("categoriesAssistanceString")
  static String getTypeSetString(CategoryOfAssistance categoryOfAssistance) {
    return categoryOfAssistance == null ? StringUtils.EMPTY : categoryOfAssistance.categoryName();
  }

  @Named("receiveDateLocalDate")
  static LocalDate getReceiveDate(String receiveDate) {
    return StringUtils.isBlank(receiveDate)
        ? LocalDate.now()
        : LocalDate.parse(receiveDate);
  }

  @Named("receiveDateToString")
  static String getReceiveDate(LocalDate receiveDate) {
    return receiveDate.toString();
  }

  @Named("receiveToBoolean")
  static boolean isReceived(String isReceive) {
    return isReceive.equals("Tак");
  }

  @Named("receiveToString")
  static String isReceived(boolean isReceive) {
    return isReceive ? "Так" : "Ні";
  }
}
