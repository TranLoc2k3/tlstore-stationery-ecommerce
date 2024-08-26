package com.tl.tlstore.tlstore.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
@NamedQueries({
        @NamedQuery(name = "Product.findByCategory_NameLike",
                query = "select p from Product p where p.category.name like :name")
})
@Data
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "sale_price")
    private Double salePrice;

    @Column(name = "percent_sale")
    private Double percentSale;

    @Column(name = "image")
    private String image;

    @Column(name = "quantity")
    private Integer quantity;
}