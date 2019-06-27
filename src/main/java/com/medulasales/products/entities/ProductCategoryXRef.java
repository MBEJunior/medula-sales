package com.medulasales.products.entities;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.persistence.*;

/////////////
// Entity and database annotations
/////////////
@Entity
@Table(
  name = "product_category_xref",
  uniqueConstraints = {
    @UniqueConstraint(name = "uk_fk_product_id_category_id", columnNames = {"fk_product_id", "fk_category_id"})
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
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)

/////////////
// Sql Queries annotations
/////////////
@SQLDelete(sql = "UPDATE product_category_xref SET fk_product_id = null, fk_category_id = null, deleted = true WHERE uuid = ?")
@Where(clause = "deleted = false")

public class ProductCategoryXRef extends AuditableEntity {

    @Column(name = "fk_product_id", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private Long productId;

    @Column(name = "fk_category_id", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private Long categoryId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_product_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_product_category_product_id"))
    private Product product;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_product_category_category_id"))
    private Category category;

    private ProductCategoryXRef(Product product, Category category) {
        this.product = product;
        this.category = category;
    }

    //
    // Soft delete behavior
    //

    @Column(name = "deleted", columnDefinition = "BOOLEAN NOT NULL DEFAULT false")
    @Setter(AccessLevel.NONE)
    private boolean deleted;

    @Column(name = "deletion_trace")
    @Setter(AccessLevel.NONE)
    String deletionTrace;

    @PreRemove
    private void beforeDelete() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fk_product_id", this.productId);
        jsonObject.put("fk_category_id", this.categoryId);
        deletionTrace = jsonObject.toString();
    }

    //
    // Factory
    //
    @Builder
    public static ProductCategoryXRef create(Product product, Category category) {
        return new ProductCategoryXRef(product, category);
    }
}
