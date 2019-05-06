package modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import util.Util;

@Entity
@Table(name = "personagem")

public class Personagem {
    private Long id;
    private String nome;
    private String sexo;
    private String classe;
    

    // Um personagem se refere a um único jogador

    private Jogador jogador;
    
    // Um personagem tem vários Equipamentos
    private List<Equipamento> equipamentos = new ArrayList<Equipamento>();

    // ********* Construtores *********

    public Personagem() {
    }

    public Personagem(String nome, String sexo, String classe, Jogador jogador) {
	this.nome = nome;
	this.sexo = sexo;
	this.classe= classe;
	this.jogador = jogador;
    }

    // ********* Métodos do Tipo Get *********

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
	return id;
    }
    
    
    @Column(nullable = false, name = "nome")
    public String getNome() {
	return nome;
    }

    @Column(name = "sexo")
    public String getSexo() {
	return sexo;
    }

    @Column(name = "classe")
    public String getClasse() {
	return classe;
    }

    // ********* Métodos do Tipo Set *********

    @SuppressWarnings("unused")
    private void setId(Long id) {
	this.id = id;
    }
    
    public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

    
    // ********* Métodos para Associações *********

    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOGADOR_ID")
    public Jogador getJogador() {
	return this.jogador;
    }

    public void setJogador(Jogador jogador) {
	this.jogador = jogador;
    }
    
    @OneToMany(mappedBy = "personagem")
    @OrderBy
    public List<Equipamento> getEquipamentos() {
	return this.equipamentos;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
	this.equipamentos = equipamentos;
    }
}