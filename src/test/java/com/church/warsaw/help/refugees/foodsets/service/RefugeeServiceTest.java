package com.church.warsaw.help.refugees.foodsets.service;

import com.church.warsaw.help.refugees.foodsets.dto.Refugee;
import com.church.warsaw.help.refugees.foodsets.dto.RegistrationInfo;
import com.church.warsaw.help.refugees.foodsets.entity.RefugeeEntity;
import com.church.warsaw.help.refugees.foodsets.repository.RefugeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefugeeServiceTest {

    @Mock
    RefugeeRepository refugeeRepository;

    @InjectMocks
    RefugeeService testee;

    RefugeeEntity refugeeEntity;

    @BeforeEach
    public void setup() {
        refugeeEntity = new RefugeeEntity();
    }

    @Test
    public void getRefugeeById_shouldReturnsRefugee() {
        String id = "someId";
        refugeeEntity.setName("Some Name");

        when(refugeeRepository.findById(id)).thenReturn(Optional.of(refugeeEntity));

        Refugee result = testee.getRefugeeById(id);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(refugeeEntity.getName());
        verify(refugeeRepository).findById(id);
    }

    @Test
    public void save_Success() {
        RegistrationInfo registrationInfo = new RegistrationInfo();
        registrationInfo.setName(refugeeEntity.getName());

        Refugee refugee = Refugee.of(registrationInfo);

        when(refugeeRepository.save(any(RefugeeEntity.class))).thenReturn(refugeeEntity);

        Refugee result = testee.save(refugee);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(refugee.getName());
        verify(refugeeRepository).save(any(RefugeeEntity.class));
    }

    @Test
    public void getRefugeeById_refugeeDoesNotExist_ThrowException() {
        String id = "someId";

        when(refugeeRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
                -> testee.getRefugeeById(id));

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).contains("Not found refugee by id =");
        verify(refugeeRepository).findById(id);
    }

    @Test
    public void findAllByIds_returnsAllRefugeesByIds() {
        String id1 = "someId1";
        String id2 = "someId2";

        HashSet<String> idList = new HashSet<>();
        idList.add(id1);
        idList.add(id2);

        List<RefugeeEntity> refugeeEntityList = new ArrayList<>();
        refugeeEntityList.add(new RefugeeEntity());
        refugeeEntityList.add(new RefugeeEntity());

        when(refugeeRepository.findAllByIdIn(idList))
                .thenReturn(refugeeEntityList);

        List<Refugee> result = testee.findAllByIds(idList);

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
        verify(refugeeRepository).findAllByIdIn(idList);
    }

}
