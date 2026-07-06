package br.com.senai.agenda_instrutores.repository;
import br.com.senai.agenda_instrutores.model.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
}
