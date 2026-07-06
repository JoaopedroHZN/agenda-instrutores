package br.com.senai.agenda_instrutores.repository;

import br.com.senai.agenda_instrutores.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
