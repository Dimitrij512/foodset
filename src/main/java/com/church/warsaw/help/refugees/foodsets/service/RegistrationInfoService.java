package com.church.warsaw.help.refugees.foodsets.service;

import static java.lang.String.format;

import com.church.warsaw.help.refugees.foodsets.config.FoodSetConfiguration;
import com.church.warsaw.help.refugees.foodsets.controller.UpdateRegistrationInfoRequest;
import com.church.warsaw.help.refugees.foodsets.dto.RegistrationInfo;
import com.church.warsaw.help.refugees.foodsets.entity.RegistrationInfoEntity;
import com.church.warsaw.help.refugees.foodsets.mapper.RegistrationInfoMapper;
import com.church.warsaw.help.refugees.foodsets.repository.RegistrationInfoRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class RegistrationInfoService {

  private final FoodSetConfiguration foodSetConfiguration;

   private final RegistrationInfoRepository repository;

  @Transactional
  public void registerForm(RegistrationInfo registrationInfo) {

    RegistrationInfoEntity regInfo =
        repository.save(RegistrationInfoMapper.INSTANCE.toEntity(registrationInfo));

    log.info("Registered form by id={}", regInfo.getId());
  }

  @Transactional
  public void updateForm(String id, UpdateRegistrationInfoRequest request) {

    if(!repository.existsById(id)) {
      throw new IllegalArgumentException(format("Not found registrationInfo by id=%s", id));
    }
    RegistrationInfoEntity entityById = repository.findById(id).get();
    entityById.setReceive(getReceived(request.getReceived()));
    entityById.setComment(request.getComment());

    repository.save(entityById);

    log.info("RegistrationInfo by id={} has been updated successfully", id);

  }

  @Transactional(readOnly = true)
  public List<RegistrationInfo> getRegistrationInfoByDate(LocalDate receiveDate) {

    List<RegistrationInfoEntity> allByReceiveDate = repository.findAllByReceiveDate(receiveDate);

    log.info("Found registrationInfos count={}, by date={}", allByReceiveDate.size(), receiveDate);

    return allByReceiveDate.stream()
        .map(RegistrationInfoMapper.INSTANCE::toDto)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<String> getAvailableStreamsByDate(LocalDate localDate) {

    List<RegistrationInfoEntity> registrationsForms = repository.findAllByReceiveDate(localDate);
    Set<String> streamsOfDelivery = foodSetConfiguration.getMapStreamsWithMaxCount().keySet();

    List<String> result = new ArrayList<>();

    streamsOfDelivery.forEach(s -> {
      int countInDb =
          (int) registrationsForms.stream().filter(r -> r.getStream().equals(s)).count();

      if (countInDb < foodSetConfiguration.getMapStreamsWithMaxCount().get(s)) {
        result.add(s);
      }
    });

    return result;
  }

  public boolean getReceived(String receiveString) {
    return "Так".equals(receiveString);
  }

}
