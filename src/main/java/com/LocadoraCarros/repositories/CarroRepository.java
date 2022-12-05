package com.LocadoraCarros.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.LocadoraCarros.entities.Carro;

public interface CarroRepository extends PagingAndSortingRepository<Carro, Integer>{
	
	public Iterable<Carro> findByModeloContainingIgnoreCase(String parteModelo);
	
	public Iterable<Carro> findByMarcaContainingIgnoreCase(String Marca);

}
