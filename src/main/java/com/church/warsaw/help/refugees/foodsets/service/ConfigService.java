package com.church.warsaw.help.refugees.foodsets.service;

import com.church.warsaw.help.refugees.foodsets.entity.ConfigEntity;
import com.church.warsaw.help.refugees.foodsets.repository.ConfigRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ConfigService {

    private final ConfigRepository configRepository;

    public void updateAvailableDates(List<LocalDate> availableDates) {
        ConfigEntity configEntity = getConfigEntity();
        configEntity.setAvailableDates(availableDates);
        configRepository.save(configEntity);
    }

    public boolean isDateAvailable(LocalDate localDate) {
        ConfigEntity globalConfig = getConfigEntity();

        return globalConfig.getAvailableDates().contains(localDate);
    }

    public ConfigEntity getConfigEntity() {
        List<ConfigEntity> configEntities = configRepository.findAll();

        if(configEntities.isEmpty()) {
           return configRepository.save(new ConfigEntity());
        }
        return configEntities.stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No global configurations found"));
    }
}

