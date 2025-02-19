package com.church.warsaw.help.refugees.foodsets.controller;

import com.church.warsaw.help.refugees.foodsets.dto.Config;
import com.church.warsaw.help.refugees.foodsets.entity.ConfigEntity;
import com.church.warsaw.help.refugees.foodsets.service.ConfigService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigControllerTest {

    @Mock
    ConfigService configService;

    @InjectMocks
    ConfigController testee;


    @Test
    void foodSetFormSuccessPage_ReturnsFoodSetFormSuccessPage() {
        String result = testee.foodSetFormSuccessPage();
        assertThat(result).isEqualTo("configForm");
    }
    @Test
    void getAvailableDates_ReturnsAllAvailableDates() {
        LocalDate availableDate = LocalDate.of(2025, 1, 22);
        List<LocalDate> availableDates = new ArrayList<>();
        availableDates.add(availableDate);
        ConfigEntity configEntity = new ConfigEntity();
        configEntity.setAvailableDates(availableDates);

        when(configService.getConfigEntity()).thenReturn(configEntity);

        List<String> result = testee.getAvailableDates();

        assertThat(result).hasSize(1);
        verify(configService).getConfigEntity();
    }

    @Test
    void updateAvailableDates_Success() {
        String availableDate = "2025-02-19";
        List<String> availableDateList = new ArrayList<>();
        availableDateList.add(availableDate);

        Config config = new Config();
        config.setAvailableDates(availableDateList);

        doNothing().when(configService).updateAvailableDates(anyList());

        testee.updateAvailableDates(config);

        verify(configService).updateAvailableDates(anyList());
    }

}
