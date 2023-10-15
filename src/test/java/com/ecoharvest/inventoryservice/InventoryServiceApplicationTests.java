package com.ecoharvest.inventoryservice;

import com.ecoharvest.inventoryservice.dto.InventoryAmountRequest;
import com.ecoharvest.inventoryservice.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InventoryServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private InventoryRepository inventoryRepository;

	private InventoryAmountRequest getInventoryAmountRequest() {
		return InventoryAmountRequest.builder()
				.productID("banana")
				.amountToUpdate(10)
				.build();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void shouldAddInventory() throws Exception {
		InventoryAmountRequest inventoryAmountRequest = getInventoryAmountRequest();
		List<InventoryAmountRequest> inventoryAmountRequestList = new ArrayList<>();
		inventoryAmountRequestList.add(inventoryAmountRequest);
		Integer currentAmount = inventoryRepository.findByProductID("banana").getProductQuantity();

		String inventoryRequestString = objectMapper.writeValueAsString(inventoryAmountRequestList);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory/add-inventory")
						.contentType(MediaType.APPLICATION_JSON)
						.content(inventoryRequestString))
				.andExpect(status().isOk());
		 Assertions.assertEquals(currentAmount + 10, inventoryRepository.findByProductID("banana").getProductQuantity());
	}

	@Test
	void shouldSubtractInventory() throws Exception {
		InventoryAmountRequest inventoryAmountRequest = getInventoryAmountRequest();
		List<InventoryAmountRequest> inventoryAmountRequestList = new ArrayList<>();
		inventoryAmountRequestList.add(inventoryAmountRequest);
		Integer currentAmount = inventoryRepository.findByProductID("banana").getProductQuantity();

		String inventoryRequestString = objectMapper.writeValueAsString(inventoryAmountRequestList);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory/subtract-inventory")
						.contentType(MediaType.APPLICATION_JSON)
						.content(inventoryRequestString))
				.andExpect(status().isOk());
		Assertions.assertEquals(currentAmount - 10, inventoryRepository.findByProductID("banana").getProductQuantity());
	}

	/* Test: Get All inventory */
	@Test
	void shouldGetAllInventory() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory"))
				.andExpect(status().isOk());
	}

	@Test
	void shouldCheckStock() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory")
						.contentType(MediaType.APPLICATION_JSON)
						.content("banana"))
				.andExpect(status().isOk());
	}
}
