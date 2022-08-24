package com.church.warsaw.help.refugees.foodsets.service;

import static java.lang.String.format;

import com.church.warsaw.help.refugees.foodsets.dto.FoodSetInfo;
import com.church.warsaw.help.refugees.foodsets.dto.Refugee;
import com.church.warsaw.help.refugees.foodsets.dto.RegistrationInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class RegistrationInfoService {

  private final RefugeeService refugeeService;

  private final FoodSetInfoService foodSetInfoService;


  @Transactional
  public void registerForm(RegistrationInfo registrationInfo) {

    Refugee refugee = refugeeService.save(Refugee.of(registrationInfo));
    foodSetInfoService.save(FoodSetInfo.of(refugee.getId(), registrationInfo));
    log.info("Registering form for refugee by id={} is successfully", refugee.getId());
  }

  @Transactional(readOnly = true)
  public List<RegistrationInfo> getRegistrationInfoByDate(LocalDate localDate) {

    List<FoodSetInfo> foodSetInfos = foodSetInfoService.findAllByDate(localDate);

    Map<String, FoodSetInfo> foodSetMap = foodSetInfos.stream()
        .collect(Collectors.toMap(FoodSetInfo::getRefugeeId, Function.identity()));

    return refugeeService.findAllByIds(foodSetMap.keySet()).stream()
        .map(r -> {
          FoodSetInfo foodSetInfo = Optional.ofNullable(foodSetMap.get(r.getId()))
              .orElseThrow(() ->
                  new IllegalArgumentException(
                      format("Not found foodSet for refugee with id = %s", r.getId())));

          return RegistrationInfo.of(r, foodSetInfo);
        }).collect(Collectors.toList());
  }

}
