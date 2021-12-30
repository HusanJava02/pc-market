package uz.pdp.apppcmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.apppcmarket.entity.*;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    private String name;

    private Integer categoryId;

    private String model;

    private Integer attachmentId;

    private Integer brandId;

    private Integer supplierId;

    private boolean active;

    private Double price;

    private String description;

    private List<Detail> details;

}
