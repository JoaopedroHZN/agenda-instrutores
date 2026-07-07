package br.com.senai.agenda_instrutores.repository;

import br.com.senai.agenda_instrutores.model.Agendamento;
import br.com.senai.agenda_instrutores.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByInstrutorIdAndDataAulaAndTurno(Long instrutorId, LocalDate dataAula, Turno turno);
}
