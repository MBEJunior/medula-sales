package com.medulasales.products.entities;
import com.medulasales.products.utils.JmUUIDGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/////////////
// Entity and database annotations
/////////////
@Entity
@Table(
  name = "category",
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
@SQLDelete(sql = "UPDATE category SET name = null, deleted = true WHERE id = ?")
@Where(clause = "deleted = false")

public class Category extends AuditableEntity {

    @Column(name = "uuid", unique=true, nullable = false, updatable= false, length = 36)
    @NotNull
    @Setter(AccessLevel.NONE)
    private String uuid;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    @NotNull
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "description", columnDefinition = "TINYTEXT")
    private String description;

    @Version
    @Setter(AccessLevel.PRIVATE)
    @EqualsAndHashCode.Include
    private long version;

    @Column(name = "fk_category_id", insertable = false, updatable = false)
    Long parentCategoryId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(
      name = "fk_category_id",
      referencedColumnName = "uuid",
      nullable = true,
      columnDefinition = "BIGINT",
      foreignKey = @ForeignKey(name = "fk_category_parent_category_id")
    )
    private Category parentCategory;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentCategory", orphanRemoval = false)
    List<Category> subCategories = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", orphanRemoval = true)
    List<ProductCategoryXRef> productCategoryXRefs = new ArrayList<>();

    private Category(@NotNull String name, String description, Category parentCategory, List<Category> subCategories, List<ProductCategoryXRef> productCategoryXRefs) {
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
        this.subCategories = subCategories;
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
    //

    @Column(name = "deleted", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    private boolean deleted;

    @Column(name = "deletion_trace")
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
    public static Category create(@NotNull String name, String description, Category parentCategory, List<Category> subCategories, List<ProductCategoryXRef> productCategoryXRefs) {
        return new Category(name, description, parentCategory, subCategories, productCategoryXRefs);
    }
}
