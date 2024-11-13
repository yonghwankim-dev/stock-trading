package site.stocktrading.api.stock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.stock.service.StockService;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockRestController {

	private final StockService service;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Void> addStock(@RequestBody String name) {
		service.saveStock(Stock.zero(name));
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<Stock>> getAllStocks() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/detail")
	public ResponseEntity<Stock> getStock(@RequestParam String name) {
		return ResponseEntity.ok(service.find(name));
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteStock(@RequestParam String name) {
		service.deleteStock(name);
		return ResponseEntity.ok().build();
	}
}
