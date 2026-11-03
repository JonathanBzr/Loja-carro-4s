package br.org.edu.ifrn.LojaCarro;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.repository.CarroRepository;
import br.org.edu.ifrn.LojaCarro.services.CarroService;
import org.junit.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarroServiceTest{

    @Test
    public void saveCarro(){
        Carro carro = new Carro();
        carro.setModelo("modelo");
        carro.setAno(1990);
        carro.setId(1L);
        carro.setPreco(10000);

        assertEquals(carro.getAno() > 1980, true);
        assertEquals(carro.getAno() > 2026, false);
        assertEquals(carro.getPreco() > 0, true);
        assertEquals(carro.getId() > 0, true);
    }

    @Test
    public void MinimoDeSeisCaracteres() {
        CarroService service = new CarroService();
        Carro carro = new Carro("Gemera", 2020, 15000.0);

        assertThrows(CarroException.class, () -> {
            service.save(carro);
        });
    }

    @Test
    public void testarValidacaoPrecoNegativo() {
        CarroService service = new CarroService();
        Carro carro = new Carro("Uno", 2015, -100.0);

        assertThrows(CarroException.class, () -> {
            service.save(carro);
        });
    }

}