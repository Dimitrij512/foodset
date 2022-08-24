package com.church.warsaw.help.refugees.foodsets.repository;

import com.church.warsaw.help.refugees.foodsets.entity.RefugeeEntity;
import java.util.List;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefugeeRepository extends MongoRepository<RefugeeEntity, String> {

  List<RefugeeEntity> findAllByIdIn(Set<String> ids);
}
