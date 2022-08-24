package com.church.warsaw.help.refugees.foodsets.mapper;

import com.church.warsaw.help.refugees.foodsets.dto.Refugee;
import com.church.warsaw.help.refugees.foodsets.entity.RefugeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RefugeeMapper {

  RefugeeMapper INSTANCE = Mappers.getMapper(RefugeeMapper.class);

  Refugee toDto(RefugeeEntity refugeeEntity);

  RefugeeEntity toEntity(Refugee refugeeEntity);
}
