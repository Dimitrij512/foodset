package com.church.warsaw.help.refugees.foodsets.mapper;

import com.church.warsaw.help.refugees.foodsets.dto.FoodSetInfo;
import com.church.warsaw.help.refugees.foodsets.entity.FoodSetInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FoodSetInfoMapper {

  FoodSetInfoMapper INSTANCE = Mappers.getMapper(FoodSetInfoMapper.class);

  FoodSetInfo toDto(FoodSetInfoEntity foodSetInfoEntity);

  FoodSetInfoEntity toEntity(FoodSetInfo foodSetInfo);
}
