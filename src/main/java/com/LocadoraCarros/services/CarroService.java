package com.LocadoraCarros.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.LocadoraCarros.entities.Carro;
import com.LocadoraCarros.repositories.CarroRepository;

@Service
public class CarroService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CarroService.class);
	
	@Autowired
	private CarroRepository carroRepository;
	
	public Carro salvarCarro(Carro carro) {
		LOGGER.info("Entrou no método salvarCarro");
		carroRepository.save(carro);
		LOGGER.info("Novo carro criado com o id: " + carro.getId());
		LOGGER.info("Saiu do método salvarCarro");
		return carro;
	}
		
	public Iterable<Carro> encontraCarroPorModelo(String modelo) {
		LOGGER.info("Entrou no método encontraCarroPorModelo");
		LOGGER.info("Procurando por carros do modelo: " + modelo);
		return carroRepository.findByModeloContainingIgnoreCase(modelo);
	}
	
	public Iterable<Carro> encontraCarroPorMarca(String marca) {
		LOGGER.info("Entrou no método encontraCarroPorMarca");
		LOGGER.info("Procurando por carros da marca: " + marca);
		return carroRepository.findByMarcaContainingIgnoreCase(marca);
	}
	
	public Carro alteraCarro(Carro carro) {
		LOGGER.info("Entrou no método alteraCarro");
		carroRepository.save(carro);
		LOGGER.info("Carro com o id: " + carro.getId() + " alterado");
		LOGGER.info("Saiu do método alteraCarro");
		return carro;
	}
	
	public Optional<Carro> excluirCarro(int id) {
		LOGGER.info("Entrou no método excluirCarro");
		try {
			carroRepository.deleteById(id);
			LOGGER.info("Carro com o id: " + id + " excluido");
		} catch (EmptyResultDataAccessException e) {
			LOGGER.info("Não existe dados para o id: " + id);
		}
		LOGGER.info("Saiu do método excluirCarro");
		return carroRepository.findById(id);
	}
}
