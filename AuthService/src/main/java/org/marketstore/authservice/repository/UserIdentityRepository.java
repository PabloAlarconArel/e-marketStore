package org.marketstore.authservice.repository;

import org.marketstore.authservice.enums.AuthProvider;
import org.marketstore.authservice.model.UserIdentity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserIdentityRepository extends MongoRepository<UserIdentity,String> {
    Optional<UserIdentity> findByUserIdAndProvider(
            String userId,
            AuthProvider provider
    );

    Optional <UserIdentity> findByProviderAndProviderId(
            AuthProvider provider,
            String providerId
    );
}
