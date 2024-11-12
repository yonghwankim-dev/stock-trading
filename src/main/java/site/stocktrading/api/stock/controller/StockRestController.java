package site.stocktrading.api.stock.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.stocktrading.api.stock.domain.Stock;
import site.stocktrading.api.stock.service.StockService;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockRestController {

	private final StockService service;

	@GetMapping
	public ResponseEntity<List<Stock>> getAllStocks() {
		return ResponseEntity.ok(service.findAll());
	}
}
