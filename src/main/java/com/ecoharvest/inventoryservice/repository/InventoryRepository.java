package com.ecoharvest.inventoryservice.repository;

import com.ecoharvest.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByProductID(String productID);
}
