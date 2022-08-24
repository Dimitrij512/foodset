package com.church.warsaw.help.refugees.foodsets.service;

import static java.lang.String.format;

import com.church.warsaw.help.refugees.foodsets.dto.Refugee;
import com.church.warsaw.help.refugees.foodsets.entity.RefugeeEntity;
import com.church.warsaw.help.refugees.foodsets.mapper.RefugeeMapper;
import com.church.warsaw.help.refugees.foodsets.repository.RefugeeRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class RefugeeService {

  private final RefugeeRepository refugeeRepository;

  @Transactional(readOnly = true)
  public Refugee getRefugeeById(String id) {
    return refugeeRepository.findById(id).map(RefugeeMapper.INSTANCE::toDto).orElseThrow(()->
        new IllegalArgumentException(format("Not found refugee by id = %s", id)));
  }

  @Transactional(readOnly = true)
  public List<Refugee> findAllByIds(Set<String> ids) {
    return refugeeRepository.findAllByIdIn(ids).stream().map(RefugeeMapper.INSTANCE::toDto)
        .collect(Collectors.toList());
  }

  @Transactional
  public Refugee save(Refugee refugee) {
    RefugeeEntity refugeeSaved = refugeeRepository.save(RefugeeMapper.INSTANCE.toEntity(refugee));
    log.info("Refugee has been stored with id={}", refugeeSaved.getId());

    return RefugeeMapper.INSTANCE.toDto(refugeeSaved);
  }


}
