package br.org.edu.ifrn.LojaCarro.controllers;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.model.Usuario;
import br.org.edu.ifrn.LojaCarro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> pesquisarTodosCarros() {
        List<Usuario> usuarios = usuarioService.listar();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/salvar")
    public ResponseEntity<Usuario> salvarCarro(@RequestBody Usuario c) {
        Usuario savedCarro = usuarioService.save(c);
        return ResponseEntity.ok(savedCarro);
    }

    // Cadastrar usuário via formulário HTML
    @PostMapping(value = "/salvar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> salvarUsuarioForm(
            @RequestParam String nome,
            @RequestParam(required = false) String cargo,
            @RequestParam(required = false, defaultValue = "0") double salario,
            @RequestParam String login,
            @RequestParam String senha) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCargo(cargo);
        usuario.setSalario(salario);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuarioService.save(usuario);
        return ResponseEntity.status(302).location(URI.create("/login.html")).build();
    }

    // Deletar carro (por ID)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario u) {
        u.setId(id);  // Define o ID no objeto
        Usuario updatedUsuario = usuarioService.update(u, id);
        return ResponseEntity.ok(updatedUsuario);
    }
}
