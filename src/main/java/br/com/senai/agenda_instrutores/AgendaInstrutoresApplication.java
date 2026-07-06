package br.com.senai.agenda_instrutores;

import br.com.senai.agenda_instrutores.model.Instrutor;
import br.com.senai.agenda_instrutores.model.Perfil;
import br.com.senai.agenda_instrutores.repository.InstrutorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AgendaInstrutoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendaInstrutoresApplication.class, args);
	}

	@Bean
	public CommandLineRunner testeBanco(InstrutorRepository repository) {
		return args -> {
			// Só tenta salvar o Alex SE a tabela estiver vazia (count == 0)!
			if (repository.count() == 0) {
				System.out.println("---  BANCO VAZIO! CADASTRANDO ADMIN PADRÃO ---");
				Instrutor adminCoped = new Instrutor("Alex Coordenador", "alex@sistemafieto.com", "senha_secreta_123", Perfil.ADMIN);
				repository.save(adminCoped);
				System.out.println("---  ADMIN CADASTRADO COM ID: " + adminCoped.getId() + " ---");
			} else {
				System.out.println("---  O BANCO JÁ TEM INSTRUTORES CADASTRADOS. PULANDO TESTE ---");
			}
		};
	}
}