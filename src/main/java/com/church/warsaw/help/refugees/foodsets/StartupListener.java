package com.church.warsaw.help.refugees.foodsets;

import static lombok.AccessLevel.PRIVATE;

import com.church.warsaw.help.refugees.foodsets.entity.RegistrationInfoEntity;
import com.church.warsaw.help.refugees.foodsets.repository.RegistrationInfoRepository;
import com.church.warsaw.help.refugees.foodsets.service.CacheStore;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Slf4j
public class StartupListener {

  private final RegistrationInfoRepository registrationInfoRepository;

  CacheStore<RegistrationInfoEntity> registrationInfoCache;


  @EventListener
  public void handleApplicationStartedEvent(ApplicationStartedEvent event) {
    LocalDate dateFrom = LocalDate.now().minusWeeks(3);
    registrationInfoRepository.findAllByReceiveDateAfter(dateFrom)
        .forEach(ri -> registrationInfoCache.add(ri.getSurname(), ri.getName(), ri));
  }
}
