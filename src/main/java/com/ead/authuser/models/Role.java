package com.ead.authuser.models;

import com.ead.authuser.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_ROLES")
public class Role implements Serializable, GrantedAuthority {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 30)
    private Roles roleName;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @JsonIgnoreProperties
    @Override
    public String getAuthority() {
        return roleName.name();
    }
}
