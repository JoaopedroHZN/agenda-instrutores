package br.com.senai.agenda_instrutores.controller;


import br.com.senai.agenda_instrutores.model.Instrutor;
import br.com.senai.agenda_instrutores.repository.InstrutorRepository;
import jakarta.validation.Valid;
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
    public ResponseEntity<Instrutor> cadastrar(@Valid @RequestBody Instrutor instrutor){
        String senhaCriptografada = passwordEncoder.encode(instrutor.getSenha());
        instrutor.setSenha(senhaCriptografada);
        Instrutor instrutorSalvo =repository.save(instrutor);
        return ResponseEntity.status(201).body(instrutorSalvo);
    }

    @PutMapping("{id}")
    public org.springframework.http.ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid Instrutor instrutorAtualizado){

        return repository.findById(id).map(instrutorAntigo ->{
            instrutorAntigo.setNome(instrutorAtualizado.getNome());
            instrutorAntigo.setEmail(instrutorAtualizado.getEmail());
            instrutorAntigo.setSenha(instrutorAtualizado.getSenha());
            instrutorAntigo.setPerfil(instrutorAtualizado.getPerfil());

            Instrutor salvo = repository.save(instrutorAntigo);

            return org.springframework.http.ResponseEntity.ok(salvo);
        }).orElse(org.springframework.http.ResponseEntity.notFound().build());
    }
}
