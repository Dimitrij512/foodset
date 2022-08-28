package com.church.warsaw.help.refugees.foodsets.mapper;

import com.church.warsaw.help.refugees.foodsets.TypeSet;
import com.church.warsaw.help.refugees.foodsets.dto.RegistrationInfo;
import com.church.warsaw.help.refugees.foodsets.entity.RegistrationInfoEntity;
import java.time.LocalDate;
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
  RegistrationInfo toDto(RegistrationInfoEntity registrationInfoEntity);

  @Mapping(source = "receive", target = "receive", qualifiedByName = "receiveToBoolean")
  @Mapping(source = "receiveDate", target = "receiveDate", qualifiedByName = "receiveDateLocalDate")
  @Mapping(source = "typeSet", target = "typeSet", qualifiedByName = "typeSetEnum")
  RegistrationInfoEntity toEntity(RegistrationInfo registrationInfo);

  @Named("typeSetEnum")
  static TypeSet getTypeSet(String typeSet) {
    return TypeSet.valueOf(typeSet);
  }

  @Named("typeSetString")
  static String getTypeSetString(TypeSet typeSet) {
    return typeSet.getTypeName();
  }

  @Named("receiveDateLocalDate")
  static LocalDate getReceiveDate(String receiveDate) {
    return LocalDate.parse(receiveDate);
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
