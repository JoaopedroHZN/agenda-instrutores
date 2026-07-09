package br.com.senai.agenda_instrutores.controller;


import br.com.senai.agenda_instrutores.model.Instrutor;
import br.com.senai.agenda_instrutores.repository.InstrutorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Dados formatados como JSON
@RequestMapping("/instrutores") // endereço http://localhost:8080/instrutores
public class InstrutorController {

    private final InstrutorRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public InstrutorController(InstrutorRepository repository, BCryptPasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Instrutor> listarTodos(){
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Instrutor> cadastrar(@RequestBody Instrutor instrutor){
        String senhaCriptografada = passwordEncoder.encode(instrutor.getSenha());
        instrutor.setSenha(senhaCriptografada);
        Instrutor instrutorSalvo =repository.save(instrutor);
        return ResponseEntity.status(201).body(instrutorSalvo);
    }
}
