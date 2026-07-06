package br.com.senai.agenda_instrutores.controller;


import br.com.senai.agenda_instrutores.model.Instrutor;
import br.com.senai.agenda_instrutores.repository.InstrutorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //Dados formatados como JSON
@RequestMapping("/instrutores") // endereço http://localhost:8080/instrutores
public class InstrutorController {

    private final InstrutorRepository repository;

    public InstrutorController(InstrutorRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Instrutor> listarTodos(){
        return repository.findAll();
    }
}
