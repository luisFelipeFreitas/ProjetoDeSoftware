package modelo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "habilidade")

public class Habilidade {
	long id;
   String nome;
   String efeito;
   double cooldown;

    // Uma habilidade se refere a um unico equipamento

    private Equipamento equipamento;

    // ********* Construtores *********

    public Habilidade() {
    }

    public Habilidade(double cooldown, String nome,String efeito, Equipamento equipamento) {
	this.cooldown = cooldown;
	this.efeito = efeito;
	this.nome = nome;
	this.equipamento = equipamento;
    }

    // ********* M�todos do Tipo Get *********

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
	return id;
    }

    @Column(nullable = false, name="cooldown")
    public double getCooldown() {
	return cooldown;
    }

    @Column(name = "efeito")
    public String getEfeito() {
	return efeito;
    }
    @Column(name = "nome")
    public String getNome() {
	return nome;
    }

    

    // ********* M�todos do Tipo Set *********

    @SuppressWarnings("unused")
    private void setId(Long id) {
	this.id = id;
    }

    public void setCooldown(double cooldown) {
	this.cooldown = cooldown;
    }

    public void setEfeito(String efeito) {
	this.efeito = efeito;
    }
	public void setNome(String nome) {
		this.nome = nome;
	}

    // ********* M�todos para Associa��es *********


	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EQUIPAMENTO_ID")
    public Equipamento getEquipamento() {
	return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
	this.equipamento = equipamento;
    }
}