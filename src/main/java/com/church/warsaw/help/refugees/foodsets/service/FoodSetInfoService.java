package com.church.warsaw.help.refugees.foodsets.service;

import static java.lang.String.format;

import com.church.warsaw.help.refugees.foodsets.dto.FoodSetInfo;
import com.church.warsaw.help.refugees.foodsets.entity.FoodSetInfoEntity;
import com.church.warsaw.help.refugees.foodsets.mapper.FoodSetInfoMapper;
import com.church.warsaw.help.refugees.foodsets.repository.FoodSetInfoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class FoodSetInfoService {

  private final FoodSetInfoRepository repository;

  public FoodSetInfo getRefugeeById(String id) {
    return repository.findById(id).map(FoodSetInfoMapper.INSTANCE::toDto).orElseThrow(()->
        new IllegalArgumentException(format("Not found foodSetInfo by id = %s", id)));
  }

  @Transactional
  public FoodSetInfo save(FoodSetInfo foodSetInfo) {
    FoodSetInfoEntity foodSetInfoSaved = repository.save(FoodSetInfoMapper.INSTANCE.toEntity(foodSetInfo));
    log.info("FoodSetInfo has been stored with id={}", foodSetInfoSaved.getId());

    return FoodSetInfoMapper.INSTANCE.toDto(foodSetInfoSaved);
  }

  @Transactional(readOnly = true)
  public List<FoodSetInfo> findAllByDate(LocalDate receiveDate) {

    return repository.findAllByReceiveDate(receiveDate).stream()
        .map(FoodSetInfoMapper.INSTANCE::toDto)
        .collect(Collectors.toList());
  }


}
