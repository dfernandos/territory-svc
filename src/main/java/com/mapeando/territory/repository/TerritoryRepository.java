package com.mapeando.territory.repository;

import com.mapeando.territory.entity.Territory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerritoryRepository extends MongoRepository<Territory, String> {
}
