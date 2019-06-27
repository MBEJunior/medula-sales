package com.medulasales.products.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)

abstract class AuditableEntity extends BaseEntity {

    /**
     * Created date
     * Created date of entity
     * generated when creating the entity
     */
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Setter(AccessLevel.NONE)
    protected Date createdDate;

    /**
     * Modified date
     * Modified date of entity
     * generated with each update of the entity
     */
    @Column(name = "modifiedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    protected Date modifiedDate;

    /**
     * Creator uuid
     * Universal Unique Identifier of the entity creator
     * Current user
     */
    @Column(name = "creator_uuid", nullable = false, updatable = false, length = 36)
    @CreatedBy
    @Setter(AccessLevel.NONE)
    protected String creatorUuid;

    /**
     * Creator uuid
     * Universal Unique Identifier of the entity modifier
     * Current user
     */
    @Column(name = "modifier_uuid", nullable = false, length = 36)
    @LastModifiedBy
    @Setter(AccessLevel.NONE)
    protected String modifierUuid;
}
