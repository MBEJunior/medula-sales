package com.medulasales.products.entities;
import com.medulasales.products.utils.JmUUIDGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/////////////
// Entity and database annotations
/////////////
@Entity
@Table(
  name = "product",
  uniqueConstraints = {
    @UniqueConstraint(name = "uk_uuid", columnNames = "uuid"),
    @UniqueConstraint(name = "uk_name", columnNames = "name")
  },
  indexes = {
    @Index(name = "idx_creator_uuid", columnList = "creator_uuid"),
    @Index(name = "idx_modifier_uuid", columnList = "modifier_uuid")
  }
)

/////////////
// Code generation annotations
/////////////
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)

/////////////
// Sql Queries annotations
/////////////
@SQLDelete(sql = "UPDATE product SET name = null, deleted = true")
@Where(clause = "deleted = false")

public class Product extends AuditableEntity {

    @Column(name = "uuid", unique=true, nullable = false, updatable= false, length = 36)
    @NotNull
    @Setter(AccessLevel.NONE)
    private String uuid;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    @NotNull
    @Length(min = 3, max = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TINYTEXT")
    private String description;

    @Column(name = "quantity", columnDefinition = "FLOAT NOT NULL DEFAULT 0")
    @Min(0)
    @NotNull
    private Float quantiy;

    @Column(name = "unit_price", columnDefinition = "FLOAT NOT NULL DEFAULT 0")
    @Min(0)
    @NotNull
    private Float unitPrice;

    @Column(name = "picture_uuid")
    private String pictureUuid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    List<ProductCategoryXRef> productCategoryXRefs = new ArrayList<>();

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    private Product(@NotNull String name, String description, @NotNull Float quantiy, @NotNull Float unitPrice, String pictureUri, List<ProductCategoryXRef> productCategoryXRefs) {
        this.name = name;
        this.description = description;
        this.quantiy = quantiy;
        this.unitPrice = unitPrice;
        this.pictureUuid = pictureUuid;
        this.productCategoryXRefs = productCategoryXRefs;
        this.uuid = (String) JmUUIDGenerator.generateDashless();
    }

    //
    // Uuid generation
    //

    @PrePersist
    private void generateUuid() {
        this.uuid = UUID.randomUUID().toString().replaceAll("-", "");
    }

    //
    // Soft delete behavior
    //pictureUuid

    @Column(name = "deleted", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    @Setter(AccessLevel.NONE)
    private boolean deleted;

    @Column(name = "deletion_trace")
    @Setter(AccessLevel.NONE)
    private String deletionTrace;

    @PreRemove
    private void beforeDelete() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", this.name);
        deletionTrace = jsonObject.toString();
    }

    //
    // Factory
    //
    @Builder
    public static Product create(@NotNull String name, String description, @NotNull Float quantiy, @NotNull Float unitPrice, String pictureUri, List<ProductCategoryXRef> productCategoryXRefs) {
        return new Product(name, description, quantiy, unitPrice, pictureUri, productCategoryXRefs);
    }
}
