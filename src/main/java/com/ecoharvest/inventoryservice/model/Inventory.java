package com.ecoharvest.inventoryservice.model;
import lombok.*;

import jakarta.persistence.*;
@Entity
@Table(name="inventory")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productID;
    private Integer productQuantity;

}
