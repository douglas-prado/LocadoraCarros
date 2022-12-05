package com.LocadoraCarros.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.LocadoraCarros.ConfigTest;
import com.LocadoraCarros.entities.Carro;
import com.LocadoraCarros.repositories.CarroRepository;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class CarroControllerTest extends ConfigTest {
	
    @Autowired
    private CarroRepository carroRepository;
	
	@Test
	public void novoCarroTest() throws Exception {
		
		JSONObject carro = new JSONObject();
		
		carro.put("marca", "teste");
		carro.put("modelo", "teste");
		carro.put("disponivel", true);
		carro.put("id", 0);
		
		mockMvc.perform(post("/api/carros/novocarro")
				.content(carro.toString())
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());

		List<Carro> carros = (List<Carro>) carroRepository.findByModeloContainingIgnoreCase("teste");

		Assertions.assertNotEquals(0, carros.get(0).getId());
        Assertions.assertEquals("teste", carros.get(0).getModelo());
		Assertions.assertEquals("teste", carros.get(0).getMarca());
		Assertions.assertEquals(true, carros.get(0).isDisponivel());
        
	}

	@Test
	public void alteraCarroTest() throws Exception {

		Carro carroTeste = new Carro("alteraCarroTest", "alteraCarroTest", true);
		carroRepository.save(carroTeste);

		JSONObject carro = new JSONObject();

		carro.put("marca", "Alterado");
		carro.put("modelo", "Alterado");
		carro.put("disponivel", false);
		carro.put("id", carroTeste.getId());

		mockMvc.perform(put("/api/carros/alteracarro")
						.content(carro.toString())
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());

		List<Carro> carros = (List<Carro>) carroRepository.findByModeloContainingIgnoreCase("Alterado");

		Assertions.assertNotEquals(0, carros.get(0).getId());
		Assertions.assertEquals("Alterado", carros.get(0).getModelo());
		Assertions.assertEquals("Alterado", carros.get(0).getMarca());
		Assertions.assertEquals(false, carros.get(0).isDisponivel());

	}

	@Test
	public void deletaCarroTest() throws Exception {

		Carro carroTeste = new Carro("deletaCarroTest", "deletaCarroTest", true);
		carroRepository.save(carroTeste);

		mockMvc.perform(delete("/api/carros/deletacarro/" + carroTeste.getId()))
				.andExpect(status().isOk());

		Optional<Carro> carro = carroRepository.findById(carroTeste.getId());

		Assertions.assertEquals(carro, Optional.empty());

	}

	@Test
	public void obterCarroPorModeloTest() throws Exception {

		Carro carro1 = new Carro("Teste", "modeloTeste", true);
		carroRepository.save(carro1);

		ResultActions retorno = mockMvc.perform(get("/api/carros/modelo?modelo=modeloTeste"))
				.andExpect(status().isOk());

		JSONArray carros = new JSONArray(retorno.andReturn()
				.getResponse()
				.getContentAsString());

		Assertions.assertEquals(carro1.getId(), carros.getJSONObject(0).get("id"));
		Assertions.assertEquals(carro1.getMarca(), carros.getJSONObject(0).get("marca"));
		Assertions.assertEquals(carro1.getModelo(), carros.getJSONObject(0).get("modelo"));
		Assertions.assertEquals(carro1.isDisponivel(), carros.getJSONObject(0).get("disponivel"));

	}

	@Test
	public void obterCarroPorMarcaTest() throws Exception {

		Carro carro1 = new Carro("marcaTeste", "carro1", true);
		Carro carro2 = new Carro("marcaTeste", "carro2", true);
		carroRepository.save(carro1);
		carroRepository.save(carro2);

		ResultActions retorno = mockMvc.perform(get("/api/carros/marca?marca=marcaTeste"))
				.andExpect(status().isOk());

		JSONArray carros = new JSONArray(retorno.andReturn()
				.getResponse()
				.getContentAsString());

		Assertions.assertEquals(carro1.getId(), carros.getJSONObject(0).get("id"));
		Assertions.assertEquals(carro1.getMarca(), carros.getJSONObject(0).get("marca"));
		Assertions.assertEquals(carro1.getModelo(), carros.getJSONObject(0).get("modelo"));
		Assertions.assertEquals(carro1.isDisponivel(), carros.getJSONObject(0).get("disponivel"));

		Assertions.assertEquals(carro2.getId(), carros.getJSONObject(1).get("id"));
		Assertions.assertEquals(carro2.getMarca(), carros.getJSONObject(1).get("marca"));
		Assertions.assertEquals(carro2.getModelo(), carros.getJSONObject(1).get("modelo"));
		Assertions.assertEquals(carro2.isDisponivel(), carros.getJSONObject(1).get("disponivel"));

	}

}
