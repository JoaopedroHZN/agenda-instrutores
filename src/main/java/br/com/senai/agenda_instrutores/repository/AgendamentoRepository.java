package br.com.senai.agenda_instrutores.repository;

import br.com.senai.agenda_instrutores.model.Agendamento;
import br.com.senai.agenda_instrutores.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByInstrutorIdAndDataAulaAndTurno(Long instrutorId, LocalDate dataAula, Turno turno);
    boolean existsBySalaAndDataAulaAndTurno(String sala, java.time.LocalDate dataAula, Turno turno);
    boolean existsByInstrutorIdAndDataAulaAndTurnoAndIdNot(Long instrutorId, java.time.LocalDate dataAula, Turno turno, Long id);
    boolean existsBySalaAndDataAulaAndTurnoAndIdNot(String sala, java.time.LocalDate dataAula, Turno turno, Long id);

    //Filtra o turno especifico
    java.util.List<Agendamento> findByTurno(Turno turno);

    //Filtra salas e turnos iguais
    java.util.List<Agendamento> findBySalaAndTurno(String sala, Turno turno);

    //filtra todas as aulas de um dia especifico
    java.util.List<Agendamento> findByDataAula(java.time.LocalDate dataAula);

    //Filtro de todas as aulas de um instrutor especifico
    java.util.List<Agendamento> findByInstrutorId(Long InstrutorId);


    java.util.List<Agendamento> findByCursoContainingIgnoreCase(String termo);



}
