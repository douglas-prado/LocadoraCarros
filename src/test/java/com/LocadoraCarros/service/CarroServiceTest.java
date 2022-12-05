package com.LocadoraCarros.service;

import com.LocadoraCarros.ConfigTest;
import com.LocadoraCarros.entities.Carro;
import com.LocadoraCarros.repositories.CarroRepository;
import com.LocadoraCarros.services.CarroService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class CarroServiceTest extends ConfigTest {

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private CarroService carroService;

    @Test
    public void novoCarroTest() throws Exception {

        Carro carroTeste = new Carro("novoCarroTest", "novoCarroTest", true);
        carroService.salvarCarro(carroTeste);

        Optional<Carro> carro = carroRepository.findById(carroTeste.getId());

        Assertions.assertEquals(carroTeste.getId(), carro.get().getId());
        Assertions.assertEquals(carroTeste.getMarca(), carro.get().getMarca());
        Assertions.assertEquals(carroTeste.getModelo(), carro.get().getModelo());
        Assertions.assertEquals(carroTeste.isDisponivel(), carro.get().isDisponivel());

    }

    @Test
    public void alteraCarroTest() throws Exception {

        Carro carroTeste = new Carro("alteraCarroTest", "alteraCarroTest", true);
        carroRepository.save(carroTeste);

        Carro carroAlterado = carroTeste;

        carroAlterado.setMarca("Alterado");
        carroAlterado.setModelo("Alterado");
        carroAlterado.setDisponivel(false);

        carroService.alteraCarro(carroAlterado);

        Optional<Carro> carro = carroRepository.findById(carroTeste.getId());

        Assertions.assertEquals(carroTeste.getId(), carro.get().getId());
        Assertions.assertEquals(carroAlterado.getMarca(), carro.get().getMarca());
        Assertions.assertEquals(carroAlterado.getModelo(), carro.get().getModelo());
        Assertions.assertEquals(carroAlterado.isDisponivel(), carro.get().isDisponivel());

    }

    @Test
    public void deletaCarroTest() throws Exception {

        Carro carroTeste = new Carro("deletaCarroTest", "deletaCarroTest", true);
        carroRepository.save(carroTeste);

        Optional<Carro> carro = carroRepository.findById(carroTeste.getId());

        Assertions.assertNotEquals(carro, Optional.empty());

        carroService.excluirCarro(carroTeste.getId());

        carro = carroRepository.findById(carroTeste.getId());

        Assertions.assertEquals(carro, Optional.empty());

    }

    @Test
    public void obterCarroPorModeloTest() throws Exception {

        Carro carroTeste = new Carro("Teste", "modeloTeste", true);
        carroRepository.save(carroTeste);

        Iterable<Carro> carros = carroService.encontraCarroPorMarca(carroTeste.getMarca());

        for (Carro carro : carros){
            Assertions.assertEquals(carroTeste.getId(), carro.getId());
            Assertions.assertEquals(carroTeste.getMarca(), carro.getMarca());
            Assertions.assertEquals(carroTeste.getModelo(), carro.getModelo());
            Assertions.assertEquals(carroTeste.isDisponivel(), carro.isDisponivel());
        }

    }

    @Test
    public void obterCarroPorMarcaTest() throws Exception {

        String marca = "marcaTeste";
        int indice = 0;

        Carro carro1 = new Carro(marca, "carro1", true);
        Carro carro2 = new Carro(marca, "carro2", true);
        carroRepository.save(carro1);
        carroRepository.save(carro2);

        List<Carro> ListaCarros = new ArrayList<>();

        ListaCarros.add(carro1);
        ListaCarros.add(carro2);

        Iterable<Carro> carros = carroService.encontraCarroPorMarca(marca);

        for (Carro carro : carros){
            Assertions.assertEquals(ListaCarros.get(indice).getId(), carro.getId());
            Assertions.assertEquals(ListaCarros.get(indice).getMarca(), carro.getMarca());
            Assertions.assertEquals(ListaCarros.get(indice).getModelo(), carro.getModelo());
            Assertions.assertEquals(ListaCarros.get(indice).isDisponivel(), carro.isDisponivel());
            indice++;
        }

    }

}
