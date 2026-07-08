package br.com.senai.agenda_instrutores.controller;


import br.com.senai.agenda_instrutores.model.Agendamento;
import br.com.senai.agenda_instrutores.repository.AgendamentoRepository;
import jakarta.validation.Valid;
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
    public org.springframework.http.ResponseEntity<?> cadastrar(@RequestBody @Valid Agendamento agendamento){
        //Vai no banco e checa se o instrutor enviado ja esta ocupado naquele dia e turno
        boolean jaExisteConflito = repository.existsByInstrutorIdAndDataAulaAndTurno(
                agendamento.getInstrutor().getId(),
                agendamento.getDataAula(),
                agendamento.getTurno()
        );
        //Se a resposta for verdade, ele devolve o erro 400
        if (jaExisteConflito){
            return org.springframework.http.ResponseEntity
                    .status(400)
                    .body("Erro: Este Instrutor já possui uma aula agendada para este mesmo dia e turno!");
        }

        //Se for falso o caminho esta limpo e ele devolve o status 200 ok
        Agendamento salvo = repository.save(agendamento);
        return org.springframework.http.ResponseEntity.ok(salvo);

    }

    @DeleteMapping("/{id}")
    public org.springframework.http.ResponseEntity<Void> deletar(@PathVariable Long id){
        //Verifica se o agendamento realmente existe no banco de dados
        if (!repository.existsById(id)){
            return org.springframework.http.ResponseEntity.notFound().build();
        }
        //Se existir ele apaga o registro
        repository.deleteById(id);

        return org.springframework.http.ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public org.springframework.http.ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid Agendamento agendamentoAtualizado){

        return repository.findById(id).map(agendamentoAntigo ->{
            agendamentoAntigo.setDataAula(agendamentoAtualizado.getDataAula());
            agendamentoAntigo.setTurno(agendamentoAtualizado.getTurno());
            agendamentoAntigo.setCurso(agendamentoAtualizado.getCurso());
            agendamentoAntigo.setUnidadeCurricular(agendamentoAtualizado.getUnidadeCurricular());
            agendamentoAntigo.setObservacoes(agendamentoAtualizado.getObservacoes());
            agendamentoAntigo.setSala(agendamentoAtualizado.getSala());
            agendamentoAntigo.setHorario(agendamentoAtualizado.getHorario());
            agendamentoAntigo.setInstrutor(agendamentoAtualizado.getInstrutor());

            Agendamento salvo = repository.save(agendamentoAntigo);

            return org.springframework.http.ResponseEntity.ok(salvo);

        }).orElse(org.springframework.http.ResponseEntity.notFound().build());
    }

}
