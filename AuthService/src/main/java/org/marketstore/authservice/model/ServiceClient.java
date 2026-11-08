package org.marketstore.authservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "services_clients")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class ServiceClient extends BaseEntity{

    private String clientId;
    private String clientSecret;
    private Set<String> roles;
    private boolean enabled;
}
