package br.com.senai.agenda_instrutores.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data da aula e obrigatoria!")
    @Column(nullable = false)
    private LocalDate dataAula;

    @NotNull(message = "O turno e obrigatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Turno turno;

    @NotBlank(message = "O nome do curso nao pode ficar em branco")
    @Column(nullable = false)
    private String curso;

    @NotBlank(message = "A unidade curricular e obrigatoria!")
    @Column(nullable = false)
    private String unidadeCurricular;

    @Column(length = 500) //Limite para 500 caracteres
    private String observacoes;

    @NotBlank(message = "A sala/laboratorio e obrigatoria!")
    @Column(nullable = false)
    private String sala;

    @NotBlank(message = "O horario da aula e obrigatorio!")
    @Column(nullable = false)
    private String horario;

    @NotNull(message = "O instrutor responsavel e obrigatorio")
    @ManyToOne // Relacionamento Muitos para um
    @JoinColumn(name = "instrutor_id", nullable = false)
    private Instrutor instrutor;

    public Agendamento() {
    }

    public Agendamento(LocalDate dataAula, Turno turno, String curso, String unidadeCurricular, String observacoes, String sala, String horario, Instrutor instrutor) {
        this.dataAula = dataAula;
        this.turno = turno;
        this.curso = curso;
        this.unidadeCurricular = unidadeCurricular;
        this.observacoes = observacoes;
        this.sala = sala;
        this.horario = horario;
        this.instrutor = instrutor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAula() {
        return dataAula;
    }

    public void setDataAula(LocalDate dataAula) {
        this.dataAula = dataAula;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getUnidadeCurricular() {
        return unidadeCurricular;
    }

    public void setUnidadeCurricular(String unidadeCurricular) {
        this.unidadeCurricular = unidadeCurricular;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }
}
