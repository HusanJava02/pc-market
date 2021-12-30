package uz.pdp.apppcmarket.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Category category;

    private String model;

    @ManyToOne
    private Attachment attachment;

    @ManyToOne(optional = false)
    private Brand brand;

    @ManyToOne(optional = false)
    private Supplier supplier;

    private boolean active;

    @Column(nullable = false)
    private Double price;

    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Detail> details;


}
