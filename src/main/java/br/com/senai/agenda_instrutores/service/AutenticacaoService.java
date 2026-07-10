package br.com.senai.agenda_instrutores.service;


import br.com.senai.agenda_instrutores.repository.InstrutorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final InstrutorRepository repository;

    public AutenticacaoService(InstrutorRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return repository.findByEmail(username);
    }
}
