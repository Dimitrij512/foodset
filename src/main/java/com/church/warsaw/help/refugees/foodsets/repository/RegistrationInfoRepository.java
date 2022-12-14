package com.church.warsaw.help.refugees.foodsets.repository;

import com.church.warsaw.help.refugees.foodsets.entity.RegistrationInfoEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistrationInfoRepository
    extends MongoRepository<RegistrationInfoEntity, String> {

  List<RegistrationInfoEntity> findAllByReceiveDate(LocalDate receiveDate);

  List<RegistrationInfoEntity> findAllByReceiveDateIsGreaterThanEqual(LocalDate startDate);

  List<RegistrationInfoEntity> findAllByReceiveDateAfter(LocalDate localDate);
}
