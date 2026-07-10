package br.com.senai.agenda_instrutores.config;


import br.com.senai.agenda_instrutores.model.Instrutor;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final String secret = "senai-coped-secreto-123";

    public String gerarToken(Instrutor instrutor){
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Agenda SENAI")
                    .withSubject(instrutor.getEmail())
                    .withClaim("id", instrutor.getId())
                    .withClaim("perfil",instrutor.getPerfil().toString())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        }catch (Exception exception){
            throw new RuntimeException("Erro interno ao gerar o Token JWT",exception);
        }
    }

    public String getSubject(String tokenJWT){
        try{
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Agenda SENAI")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }catch (com.auth0.jwt.exceptions.JWTVerificationException exception){
            throw new RuntimeException("Token JWT invalido ou expirado!");
        }
    }

    private Instant dataExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
