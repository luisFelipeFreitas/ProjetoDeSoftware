package modelo;

import java.util.ArrayList;
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

@Entity
@Table(name = "equipamento")

public class Equipamento {
    private Long id;
    private String nome;
    private String tipo;
    private String elemento;
    

    // Um Equipamento se refere a um único personagem

    private Personagem personagem;
    
    // Um Equipamento tem vários atributos
    private List<Habilidade> habilidades = new ArrayList<Habilidade>();

    // ********* Construtores *********

    public Equipamento() {
    }

    public Equipamento(String nome, String tipo, String elemento, Personagem personagem) {
	this.nome = nome;
	this.tipo = tipo;
	this.elemento= elemento;
	this.personagem = personagem;
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

    @Column(name = "tipo")
    public String getTipo() {
	return tipo;
    }

    @Column(name = "Elemento")
    public String getElemento() {
	return elemento;
    }

    // ********* Métodos do Tipo Set *********

    @SuppressWarnings("unused")
    private void setId(Long id) {
	this.id = id;
    }
    
    public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setElemento(String elemento) {
		this.elemento = elemento;
	}

    
    // ********* Métodos para Associações *********

    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSONAGEM_ID")
    public Personagem getPersonagem() {
	return this.personagem;
    }

    public void setPersonagem(Personagem personagem) {
	this.personagem = personagem;
    }
    
    @OneToMany(mappedBy = "equipamento")
    @OrderBy
    public List<Habilidade> getHabilidades() {
	return this.habilidades;
    }

    public void setHabilidades(List<Habilidade> habilidades) {
	this.habilidades = habilidades;
    }
}