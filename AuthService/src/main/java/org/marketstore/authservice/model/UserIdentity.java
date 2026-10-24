package org.marketstore.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.marketstore.authservice.enums.AuthProvider;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user_identities")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(
        name = "provider_provideId_unique",
        def = "{'provider': 1, 'providerId':1}",
        unique = true,
        partialFilter = "{'providerId': {'$exists': true, '$ne': null}}"
)

public class UserIdentity extends BaseEntity {

    private String userId;
    private AuthProvider provider;
    private String providerId;
    private String passwordHash;



}
