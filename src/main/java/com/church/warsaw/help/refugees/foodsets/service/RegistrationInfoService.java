package com.church.warsaw.help.refugees.foodsets.service;

import com.church.warsaw.help.refugees.foodsets.config.FoodSetConfiguration;
import com.church.warsaw.help.refugees.foodsets.controller.UpdateRegistrationInfoRequest;
import com.church.warsaw.help.refugees.foodsets.dto.RegistrationInfo;
import com.church.warsaw.help.refugees.foodsets.email.EmailService;
import com.church.warsaw.help.refugees.foodsets.entity.RegistrationInfoEntity;
import com.church.warsaw.help.refugees.foodsets.mapper.RegistrationInfoMapper;
import com.church.warsaw.help.refugees.foodsets.repository.RegistrationInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@AllArgsConstructor
@Service
public class RegistrationInfoService {

  public static final String HAS_ERROR_KEY = "hasErrorKey";

  private final EmailService emailService;

  private final FoodSetConfiguration foodSetConfiguration;

   private final RegistrationInfoRepository repository;


   private final ConfigService configService;

  CacheStore<RegistrationInfoEntity> registrationInfoCache;


  @Transactional
  public Pair<String, String> registerForm(RegistrationInfo registrationInfo) {

    RegistrationInfoEntity latestRegistrationInfo
        = registrationInfoCache.get(registrationInfo.getSurname(), registrationInfo.getName());

    if((Objects.isNull(latestRegistrationInfo))
        || isLatestReceivedDateBiggerThanExpectedWeeks(latestRegistrationInfo.getReceiveDate())) {

      RegistrationInfoEntity regInfo =
          repository.save(RegistrationInfoMapper.INSTANCE.toEntity(registrationInfo));
      registrationInfoCache.add(regInfo.getSurname(), registrationInfo.getName(), regInfo);

      emailService.sendMail(registrationInfo.getEmail(),
          registrationInfo.getReceiveDate(),
          registrationInfo.getStream());

      return Pair.of(regInfo.getId(), null);
    }

    return Pair.of(HAS_ERROR_KEY,
        format("Перевищена кількікість реєстрацій. \n" +
                "Орієнтована дата \n" +
                "наступної реєстрації %s",
            latestRegistrationInfo.getReceiveDate().plusWeeks(3)));
  }

  @Transactional
  public void registerFormFree(RegistrationInfo registrationInfo) {
    RegistrationInfoEntity regInfo =
        repository.save(RegistrationInfoMapper.INSTANCE.toEntity(registrationInfo));
    log.info("Registered form by id={} without validation", regInfo.getId());
  }

  @Transactional
  public void updateForm(String id, UpdateRegistrationInfoRequest request) {

    RegistrationInfoEntity entityById = repository.findById(id).orElseThrow(() ->
        new IllegalArgumentException(format("Not found registrationInfo by id=%s", id)));
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
  public List<RegistrationInfo> getRegistrationInfoByRange(LocalDate startDate, LocalDate endDate) {

    List<RegistrationInfoEntity> allByReceiveDate =
        repository.findAllByReceiveDateIsGreaterThanEqual(startDate).stream()
            .filter(r -> r.getReceiveDate().isBefore(endDate)
                || r.getReceiveDate().isEqual(startDate))
            .collect(Collectors.toList());

    log.info("Found registrationInfos count={}, by the range startDate={} and endDate={}", allByReceiveDate.size(), startDate, endDate);

    return allByReceiveDate.stream()
        .map(RegistrationInfoMapper.INSTANCE::toDto)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<String> getAvailableStreamsByDate(LocalDate localDate) {

    if (!configService.isDateAvailable(localDate)) {
      log.info("Date = {} is not available for registration", localDate);
      return Collections.emptyList();
    }

    List<RegistrationInfoEntity> registrationsForms = repository.findAllByReceiveDate(localDate);
    Map<String, Integer> streamWithCountByDay = getStreamsWithCount(localDate);

    Set<String> streamsOfDelivery = streamWithCountByDay.keySet();
    List<String> result = new ArrayList<>();

    streamsOfDelivery.forEach(s -> {
      int countInDb =
          (int) registrationsForms.stream().filter(r -> r.getStream().equals(s)).count();

      if (countInDb < streamWithCountByDay.get(s)) {
        result.add(s);
      }
    });

    return result;
  }

  private boolean getReceived(String receiveString) {
    return "Так".equals(receiveString);
  }

  private boolean isLatestReceivedDateBiggerThanExpectedWeeks(LocalDate latestReceivedDate) {
    LocalDate weeksFromLatestReceivedDate = latestReceivedDate.plusWeeks(foodSetConfiguration.getReceiveOnceForWeeks());
    LocalDate today = LocalDate.now();

    return today.isEqual(weeksFromLatestReceivedDate) || today.isAfter(weeksFromLatestReceivedDate);
  }

  private Map<String, Integer> getStreamsWithCount(LocalDate date) {
    DayOfWeek dayOfWeek = date.getDayOfWeek();

    switch (dayOfWeek) {
      case MONDAY:
        return foodSetConfiguration.getMondayConfiguration();
      case TUESDAY:
        return foodSetConfiguration.getTuesdayConfiguration();
      case WEDNESDAY:
        return foodSetConfiguration.getWednesdayConfiguration();
      case THURSDAY:
        return foodSetConfiguration.getThursdayConfiguration();
      case FRIDAY:
        return foodSetConfiguration.getFridayConfiguration();
      default:
        return Collections.emptyMap();
    }
  }

}
