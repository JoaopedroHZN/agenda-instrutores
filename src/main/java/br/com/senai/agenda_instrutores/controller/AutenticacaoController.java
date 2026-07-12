package br.com.senai.agenda_instrutores.controller;


import br.com.senai.agenda_instrutores.config.TokenService;
import br.com.senai.agenda_instrutores.model.DadosLogin;
import br.com.senai.agenda_instrutores.model.DadosTokenJWT;
import br.com.senai.agenda_instrutores.model.Instrutor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody  @Valid DadosLogin dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        //Se a senha estiver errada ele joga um erro 403
        var authentication = manager.authenticate(authenticationToken);

        //Se a senha estiver certa pega o instrutor e fabrica o Token
        var tokenJWT = tokenService.gerarToken((Instrutor) authentication.getPrincipal());


        return  ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }


}
