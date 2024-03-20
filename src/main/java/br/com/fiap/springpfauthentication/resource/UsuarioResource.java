package br.com.fiap.springpfauthentication.resource;

import br.com.fiap.springpfauthentication.dto.response.PessoaResponse;
import br.com.fiap.springpfauthentication.dto.request.UsuarioRequest;
import br.com.fiap.springpfauthentication.dto.response.UsuarioResponse;
import br.com.fiap.springpfauthentication.entity.Pessoa;
import br.com.fiap.springpfauthentication.entity.Usuario;
import br.com.fiap.springpfauthentication.repository.UsuarioRepository;
import br.com.fiap.springpfauthentication.service.UsuarioService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioRepository repo;


    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioResponse> findAll() {
        return repo.findAll().stream().map(service::toResponse).toList();
    }

    @GetMapping(value = "/{id}")
    public UsuarioResponse findById(@PathVariable Long id) {
        Usuario usuario = repo.findById(id).orElseThrow();
        return service.toResponse(usuario);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<UsuarioResponse> save(@RequestBody UsuarioRequest u) {
        if (Objects.isNull(u.pessoa())) return null;

        Usuario entity = service.toEntity(u);

        Usuario salve = repo.save(entity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        UsuarioResponse body = service.toResponse(repo.save(service.toEntity(u)));

        return ResponseEntity.created(uri).body(body);
    }


}
