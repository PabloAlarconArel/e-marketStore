package org.marketstore.authservice.model;

import lombok.experimental.SuperBuilder;
import org.marketstore.authservice.enums.Role;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "users")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor


public class User extends BaseEntity{

    private String name;
    private String email;
    private Set<Role> roles;
    private String image;


}
