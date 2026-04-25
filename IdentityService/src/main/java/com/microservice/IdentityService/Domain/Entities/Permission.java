package com.microservice.IdentityService.Domain.Entities;

import com.microservice.IdentityService.Domain.Common.BaseEntity;
import com.microservice.IdentityService.Domain.Common.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Getter
@Setter
public class Permission extends SoftDeleteEntity {

    private String name;

    @Column(unique = true)
    private String slug;

    private String resource;

    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;
}