package com.church.warsaw.help.refugees.foodsets.repository;

import com.church.warsaw.help.refugees.foodsets.entity.ConfigEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<ConfigEntity, String> {
}

