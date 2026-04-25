package com.microservice.IdentityService.Domain.Entities;

import com.microservice.IdentityService.Domain.Common.BaseEntity;
import com.microservice.IdentityService.Domain.Common.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role extends SoftDeleteEntity {

    private String name;

    @Column(unique = true)
    private String slug;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<com.microservice.IdentityService.Domain.Entities.Permission> permissions;
}