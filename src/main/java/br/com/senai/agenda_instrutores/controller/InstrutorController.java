package br.com.senai.agenda_instrutores.controller;


import br.com.senai.agenda_instrutores.model.Instrutor;
import br.com.senai.agenda_instrutores.repository.InstrutorRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


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
    public org.springframework.data.domain.Page<Instrutor> listarTodos(org.springframework.data.domain.Pageable paginacao){
        return repository.findAll(paginacao);
    }


    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Instrutor> cadastrar(@Valid @RequestBody Instrutor instrutor){
        String senhaCriptografada = passwordEncoder.encode(instrutor.getSenha());
        instrutor.setSenha(senhaCriptografada);
        Instrutor instrutorSalvo =repository.save(instrutor);
        return ResponseEntity.status(201).body(instrutorSalvo);
    }

    @PutMapping("/{id}")
    public org.springframework.http.ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid Instrutor instrutorAtualizado){

        return repository.findById(id).map(instrutorAntigo ->{
            instrutorAntigo.setNome(instrutorAtualizado.getNome());
            instrutorAntigo.setEmail(instrutorAtualizado.getEmail());
            if (instrutorAtualizado.getSenha() != null && !instrutorAtualizado.getSenha().isBlank()){
                String novaSenhaCriptografada = passwordEncoder.encode(instrutorAtualizado.getSenha());
                instrutorAntigo.setSenha(novaSenhaCriptografada);
            }
            instrutorAntigo.setPerfil(instrutorAtualizado.getPerfil());

            Instrutor salvo = repository.save(instrutorAntigo);

            return org.springframework.http.ResponseEntity.ok(salvo);
        }).orElse(org.springframework.http.ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public org.springframework.http.ResponseEntity<Void> deletar(@PathVariable Long id){
        //Se nao existe o instrutor ele da nao encontrado
        if(!repository.existsById(id)){
            return org.springframework.http.ResponseEntity.notFound().build();
        }
        //Se existir ele deleta o id passado como argumento
        repository.deleteById(id);

        return org.springframework.http.ResponseEntity.noContent().build();
    }


}
