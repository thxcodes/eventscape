package com.thxcodes.eventscape.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "data_hora_evento", nullable = false)
    private LocalDateTime dataHoraEvento;

    @Column(length = 200)
    private String local;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    // Construtor sem parâmetros
    public Evento() {
    }

    // Construtor com parâmetros
    public Evento(Long id, String nome, LocalDateTime dataHoraEvento, String local, String descricao) {
        this.id = id;
        this.nome = nome;
        this.dataHoraEvento = dataHoraEvento;
        this.local = local;
        this.descricao = descricao;
    }

    // Getters e Setters (gerados automaticamente ou implementados manualmente)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getDataHoraEvento() {
        return dataHoraEvento;
    }

    public void setDataHoraEvento(LocalDateTime dataHoraEvento) {
        this.dataHoraEvento = dataHoraEvento;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
