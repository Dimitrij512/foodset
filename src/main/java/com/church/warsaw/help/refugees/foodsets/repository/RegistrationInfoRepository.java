package com.church.warsaw.help.refugees.foodsets.repository;

import com.church.warsaw.help.refugees.foodsets.entity.RegistrationInfoEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistrationInfoRepository
    extends MongoRepository<RegistrationInfoEntity, String> {

  List<RegistrationInfoEntity> findAllByReceiveDate(LocalDate receiveDate);

  List<RegistrationInfoEntity> findAllByReceiveDateIsGreaterThanEqual(LocalDate startDate);

  List<RegistrationInfoEntity> findAllByPhoneNumberAndSurname(String phoneNumber, String surname);
  List<RegistrationInfoEntity> findAllBySurnameIgnoreCaseAndNameIgnoreCase(String surname, String name);


}
