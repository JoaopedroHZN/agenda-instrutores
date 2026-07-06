package br.com.senai.agenda_instrutores.controller;


import br.com.senai.agenda_instrutores.model.Agendamento;
import br.com.senai.agenda_instrutores.repository.AgendamentoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/agendamentos")
public class AgendamentoController {

    private final AgendamentoRepository repository;

    public AgendamentoController(AgendamentoRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Agendamento> listarTodos(){
        return repository.findAll();
    }

    @PostMapping
    public Agendamento cadastrar(@RequestBody Agendamento agendamento){
        return repository.save(agendamento);
    }
}
