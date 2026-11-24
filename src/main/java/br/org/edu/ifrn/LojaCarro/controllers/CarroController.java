
package br.org.edu.ifrn.LojaCarro.controllers;

import br.org.edu.ifrn.LojaCarro.CarroException;
import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.services.CarroService;
import br.org.edu.ifrn.LojaCarro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @Autowired
    private UsuarioService usuarioService;

    // Salvar carro (corrigido para POST)
    @PostMapping("salvar")
    public ResponseEntity<Carro> salvarCarro(@RequestBody Carro c) {
        Carro savedCarro = carroService.save(c);
        return ResponseEntity.ok(savedCarro);
    }

    // Atualizar carro (por ID)
    @PutMapping("/atualizar{id}")
    public ResponseEntity<Carro> atualizarCarro(@PathVariable Long id, @RequestBody Carro c) {
        c.setId(id);  // Define o ID no objeto
        Carro updatedCarro = carroService.update(c);
        return ResponseEntity.ok(updatedCarro);
    }

    // Deletar carro (por ID)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCarro(@PathVariable Long id) {
        carroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Pesquisar carro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Carro> pesquisarCarroPorId(@PathVariable Long id) {
        Optional<Carro> carro = carroService.findById(id);
        return carro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Buscar carro por modelo via formulário HTML
    @GetMapping("/buscar")
    public ResponseEntity<Carro> buscarPorModelo(@RequestParam String modelo) {
        Optional<Carro> carro = carroService.findByModelo(modelo);
        return carro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Pesquisar todos os carros
    @GetMapping("/listarCarros")
    public ResponseEntity<List<Carro>> pesquisarTodosCarros() {
        List<Carro> carros = carroService.findAll();
        return ResponseEntity.ok(carros);
    }

    @PostMapping(value = "/getCarro", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Carro> pesquisarCarroPorModelo(@RequestBody String modelo) {
        Optional<Carro> carro = carroService.findByModelo(modelo.trim());
        return carro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/deleteCarro", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deletarCarroPorModelo(@RequestBody String modelo) {
        Carro carro = carroService.deleteByModelo(modelo.trim());
        return ResponseEntity.ok("Carro deletado: " + carro.getModelo());
    }

    @PostMapping(value = "/updateCarro", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Carro> atualizarCarroPorModelo(@RequestBody String payload) {
        String[] partes = payload.split(",", 2);
        if (partes.length < 2) {
            throw new CarroException("Payload inválido. Use modelo,preco.");
        }
        String modelo = partes[0].trim();
        double preco = parsePreco(partes[1].trim());
        Carro updatedCarro = carroService.updateByModelo(modelo, preco);
        return ResponseEntity.ok(updatedCarro);
    }

    // Adicionar carro via formulário HTML
    @PostMapping(value = "/adicionar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> adicionarCarro(
            @RequestParam String modelo,
            @RequestParam int ano,
            @RequestParam double preco) {
        Carro novoCarro = new Carro(modelo, ano, preco);
        carroService.save(novoCarro);
        return ResponseEntity.status(302).location(URI.create("/index.html")).build();
    }

    // Deletar carro via formulário HTML
    @PostMapping(value = "/deletar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> deletarCarroForm(@RequestParam String modelo) {
        carroService.deleteByModelo(modelo.trim());
        return ResponseEntity.status(302).location(URI.create("/index.html")).build();
    }

    // Atualizar carro via formulário HTML
    @PostMapping(value = "/atualizar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> atualizarCarroForm(
            @RequestParam String modelo,
            @RequestParam double preco) {
        carroService.updateByModelo(modelo.trim(), preco);
        return ResponseEntity.status(302).location(URI.create("/index.html")).build();
    }

    private double parsePreco(String preco) {
        try {
            return Double.parseDouble(preco);
        } catch (NumberFormatException ex) {
            throw new CarroException("Preço inválido: " + preco);
        }
    }
}