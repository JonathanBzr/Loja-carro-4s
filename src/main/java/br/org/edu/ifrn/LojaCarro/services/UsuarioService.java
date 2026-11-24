package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Usuario;
import br.org.edu.ifrn.LojaCarro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario update(Usuario novo, Long id) {
        Usuario antigo = usuarioRepository.findById(id).orElse(null);
        antigo.setNome(novo.getNome());
        antigo.setCargo(novo.getCargo());
        antigo.setSalario(novo.getSalario());
        antigo.setLogin(novo.getLogin());
        if (novo.getSenha() != null && !novo.getSenha().isBlank()) {
            antigo.setSenha(passwordEncoder.encode(novo.getSenha()));
        }
        return usuarioRepository.save(antigo);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).get();
    }
}
