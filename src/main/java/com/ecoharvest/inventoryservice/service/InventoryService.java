package com.ecoharvest.inventoryservice.service;

import com.ecoharvest.inventoryservice.dto.InventoryAmountRequest;
import com.ecoharvest.inventoryservice.dto.InventoryResponse;
import com.ecoharvest.inventoryservice.exception.InventoryException;
import com.ecoharvest.inventoryservice.model.Inventory;
import com.ecoharvest.inventoryservice.repository.InventoryRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    //find product inventory by product id
    public boolean isInStock (String productID) {
        Inventory inventory  = inventoryRepository.findByProductID(productID);
        if (inventory == null || inventory.getProductQuantity() == null) {
            return false;
        }
        return inventory.getProductQuantity() > 0 ? true : false;
    }

    public List<InventoryResponse> getAllInventory() {
        List<Inventory> inventory = inventoryRepository.findAll();

        return inventory.stream().map(this::mapToInventoryResponse).toList();
    }

    private InventoryResponse mapToInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .productID(inventory.getProductID())
                .amount(inventory.getProductQuantity())
                .build();
    }
    //add product quantity
    public void addInventory (List<InventoryAmountRequest> amountList) {
        log.info("inventory." + amountList);
        for(int i = 0; i<amountList.size();i++) {
            Inventory inventory = inventoryRepository.findByProductID(amountList.get(i).getProductID());
            log.info("This is a test inventory." + inventory);
            if (inventory == null || inventory.getProductQuantity() == null) {
                // throw new InventoryException("invalid product ID found");
                Inventory inventoryToAdd = Inventory.builder()
                        .productID(amountList.get(i).getProductID())
                        .productQuantity(amountList.get(i).getAmountToUpdate())
                        .build();

                inventoryRepository.save(inventoryToAdd);
                 break;
            }
            log.info("This is a test inventory." + amountList.get(i).getAmountToUpdate());
            inventory.setProductQuantity(inventory.getProductQuantity()+amountList.get(i).getAmountToUpdate());
            inventoryRepository.save(inventory);
        }
    }

    //subtract product quantity
    public void subtractInventory (List<InventoryAmountRequest> amountList) {
        for (int i = 0; i < amountList.size(); i++) {
            Inventory inventory = inventoryRepository.findByProductID(amountList.get(i).getProductID());
            if (inventory == null || inventory.getProductQuantity() == null) {
                // throw new InventoryException("invalid product ID found");
                 //break;
                continue;
            }
            if(inventory.getProductQuantity() <=amountList.get(i).getAmountToUpdate()) {
                inventory.setProductQuantity(0);
            } else {
                inventory.setProductQuantity(inventory.getProductQuantity() - amountList.get(i).getAmountToUpdate());
            }
            inventoryRepository.save(inventory);
        }
    }

}
