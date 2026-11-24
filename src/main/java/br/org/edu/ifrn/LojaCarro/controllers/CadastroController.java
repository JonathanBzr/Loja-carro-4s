package br.org.edu.ifrn.LojaCarro.controllers;

import br.org.edu.ifrn.LojaCarro.model.Usuario;
import br.org.edu.ifrn.LojaCarro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class CadastroController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> cadastrar(
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
}
