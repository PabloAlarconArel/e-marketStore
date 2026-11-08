package org.marketstore.authservice.repository;

import org.marketstore.authservice.model.ServiceClient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ServiceClientRepository extends MongoRepository<ServiceClient,String> {

    Optional<ServiceClient> findByClientId(String clientId);
}
