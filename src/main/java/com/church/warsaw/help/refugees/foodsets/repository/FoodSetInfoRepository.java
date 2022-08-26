package com.church.warsaw.help.refugees.foodsets.repository;

import com.church.warsaw.help.refugees.foodsets.entity.FoodSetInfoEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodSetInfoRepository extends MongoRepository<FoodSetInfoEntity, String> {

  List<FoodSetInfoEntity> findAllByReceiveDate(LocalDate receiveDate);
}
