package br.com.senai.agenda_instrutores.controller;


import br.com.senai.agenda_instrutores.model.Agendamento;
import br.com.senai.agenda_instrutores.model.Turno;
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


    @GetMapping("/buscar")
    public org.springframework.http.ResponseEntity<java.util.List<Agendamento>> buscarPorTurno(@RequestParam Turno turno){
        java.util.List<Agendamento> listaFiltrada = repository.findByTurno(turno);

        return org.springframework.http.ResponseEntity.ok(listaFiltrada);
    }

    @GetMapping("/buscar-duplo")
    public org.springframework.http.ResponseEntity<java.util.List<Agendamento>> buscarPorSalaETurno(
            @RequestParam String sala,
            @RequestParam Turno turno)
    {java.util.List<Agendamento> listaFiltrada = repository.findBySalaAndTurno(sala, turno);

        return org.springframework.http.ResponseEntity.ok(listaFiltrada);
    }

    @GetMapping("/buscar-data")
    public org.springframework.http.ResponseEntity<java.util.List<Agendamento>> buscarPorData(
            @RequestParam java.time.LocalDate data
    ){
        java.util.List<Agendamento> aulasDoDia = repository.findByDataAula(data);
        return org.springframework.http.ResponseEntity.ok(aulasDoDia);
    }

    @GetMapping("/buscar-professor")
    public org.springframework.http.ResponseEntity<java.util.List<Agendamento>> buscarPorProfessor(
            @RequestParam Long id
    ){
        java.util.List<Agendamento> aulasDoProfessor = repository.findByInstrutorId(id);
        return org.springframework.http.ResponseEntity.ok(aulasDoProfessor);
    }

    @PostMapping
    public org.springframework.http.ResponseEntity<?> cadastrar(@RequestBody Agendamento agendamento){
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

        boolean salaocupada = repository.existsBySalaAndDataAulaAndTurno(
                agendamento.getSala(),
                agendamento.getDataAula(),
                agendamento.getTurno()
        );

        if (salaocupada){
            return org.springframework.http.ResponseEntity
                    .badRequest()
                    .body("Erro: Esta sala ou laboratorio ja esta ocupado neste dia e turno!");
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
    public org.springframework.http.ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Agendamento agendamentoAtualizado){

        return repository.findById(id).map(agendamentoAntigo ->{

            boolean instrutorOcupado = repository.existsByInstrutorIdAndDataAulaAndTurnoAndIdNot(
                    agendamentoAtualizado.getInstrutor().getId(),
                    agendamentoAtualizado.getDataAula(),
                    agendamentoAtualizado.getTurno(),
                    id
            );

            if (instrutorOcupado){
                return org.springframework.http.ResponseEntity
                        .badRequest()
                        .body("Erro: O instrutor ja esta ocupado em outra aula neste turno!");
            }

            boolean salaOcupada = repository.existsBySalaAndDataAulaAndTurnoAndIdNot(
                    agendamentoAtualizado.getSala(),
                    agendamentoAtualizado.getDataAula(),
                    agendamentoAtualizado.getTurno(),
                    id
            );

            if (salaOcupada){
                return org.springframework.http.ResponseEntity
                        .badRequest()
                        .body("Erro: Esta sala ou laboratorio ja esta ocupada por outra turma neste turno!");
            }

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
