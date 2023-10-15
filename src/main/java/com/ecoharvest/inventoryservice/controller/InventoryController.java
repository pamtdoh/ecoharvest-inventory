package com.ecoharvest.inventoryservice.controller;

import com.ecoharvest.inventoryservice.dto.InventoryAmountRequest;
import com.ecoharvest.inventoryservice.dto.InventoryResponse;
import com.ecoharvest.inventoryservice.model.Inventory;
import com.ecoharvest.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getAllProductInventory()  {
        return  inventoryService.getAllInventory();
    }


    @GetMapping("/{product-id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("product-id") String productID) {
        return inventoryService.isInStock(productID);
    }

//    @GetMapping("/{product-id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void deleteInventory(@PathVariable("product-id") String productID) {
//        inventoryService.deleteInventory(productID);
//    }

    @PostMapping("add-inventory")
    @ResponseStatus(HttpStatus.OK)
    public void addStock(@RequestBody List<InventoryAmountRequest> amountList) {
        inventoryService.addInventory(amountList);
    }

    @PostMapping("subtract-inventory")
    @ResponseStatus(HttpStatus.OK)
    public void subtractStock(@RequestBody List<InventoryAmountRequest> amountList) {
        inventoryService.subtractInventory(amountList);
    }

}
