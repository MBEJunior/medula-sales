package com.medulasales.products.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@MappedSuperclass
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

abstract class BaseEntity implements Serializable {

    /**
     * Id
     * Generic uuid for entities
     * generated when creating the entity
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private Long id;
}
