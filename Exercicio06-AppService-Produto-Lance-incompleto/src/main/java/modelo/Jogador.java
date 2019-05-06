package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


@Entity
@Table(name = "jogador")

public class Jogador {
    private Long id;
    private String username;
    private String senha;
    private String email;

    // Um jogador possui muitos personagens

    private List<Personagem> personagens = new ArrayList<Personagem>();

    // ********* Construtores *********

    public Jogador() {
    }

    public Jogador(String username, String senha, String email) {
	this.username=username;
	this.senha=senha;
	this.email=email;

    }

    // ********* Métodos do Tipo Get *********

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
	return id;
    }

    @Column(name= "username")
    public String getUsername() {
	return username;
    }

    @Column(name= "senha")
    public String getSenha() {
	return senha;
    }
    
    @Column(name= "email")
    public String getEmail() {
		return email;
	}

    // ********* Métodos do Tipo Set *********

    @SuppressWarnings("unused")
	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    // ********* Métodos para Associações *********
	/*
     * Com o atributo mappedBy. Sem ele a JPA irá procurar pela tabela
     * PRODUTO_LANCE. Com ele, ao se tentar recuperar um produto e todos os seus
     * lances, o join de PRODUTO e LANCE irá acontecer através da chave estrangeira
     * existente em LANCE. Sem ele a JPA irá procurar pela tabela PRODUTO_LANCE.
     */
    @OneToMany(mappedBy = "jogador")
    @OrderBy
    public List<Personagem> getPersonagens() {
	return this.personagens;
    }

    public void setPersonagens(List<Personagem> personagens) {
	this.personagens = personagens;
    }
}
