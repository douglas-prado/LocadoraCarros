package com.LocadoraCarros.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.LocadoraCarros.entities.Carro;
import com.LocadoraCarros.services.CarroService;

@RestController
@RequestMapping(path = "/api/carros")
public class CarroController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CarroController.class);
	
	@Autowired
	private CarroService carroService;
	
	@PostMapping(path = "/novocarro")
	public @ResponseBody Carro novoCarro(@RequestBody Carro carro) {
		LOGGER.info("Entrou no método novoCarro");
		carroService.salvarCarro(carro);
		LOGGER.info("Saiu do método novoCarro");
		return carro;
	}
	
	@GetMapping(path = "/modelo")
	public Iterable<Carro> obterCarroPorModelo(String modelo) {
		LOGGER.info("Entrou no metodo obterCarroPorModelo");
		return carroService.encontraCarroPorModelo(modelo);
	}
	
	@GetMapping(path = "/marca")
	public Iterable<Carro> obterCarroPorMarca(String marca) {
		LOGGER.info("Entrou no método obterCarroPorMarca");
		return carroService.encontraCarroPorMarca(marca);
	}
	
	@PutMapping(path = "/alteracarro")
	public Carro alteraCarro(@RequestBody Carro carro) {
		LOGGER.info("Entrou no método alteraCarro");
		return carroService.alteraCarro(carro);
	}
	
	@DeleteMapping(path = "/deletacarro/{id}")
	public Optional<Carro> excluirCarro(@PathVariable int id) {
		LOGGER.info("Entrou no método excluirCarro");
		return carroService.excluirCarro(id);
	}
	
}
