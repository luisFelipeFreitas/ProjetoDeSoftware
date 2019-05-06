package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;

import dao.JogadorDAO;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import modelo.Jogador;
import util.JPAUtil;

public class JogadorDAOImpl implements JogadorDAO {
    public long inclui(Jogador umJogador) {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    em.persist(umJogador);

	    return umJogador.getId();
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    public void altera(Jogador umJogador) throws ObjetoNaoEncontradoException {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    Jogador Jogador = em.find(Jogador.class, umJogador.getId(), LockModeType.PESSIMISTIC_WRITE);

	    if (Jogador == null) {
		throw new ObjetoNaoEncontradoException();
	    }

	    em.merge(umJogador);
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    public void exclui(long id) throws ObjetoNaoEncontradoException {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    Jogador Jogador = em.find(Jogador.class, id, LockModeType.PESSIMISTIC_WRITE);

	    if (Jogador == null) {
		throw new ObjetoNaoEncontradoException();
	    }

	    em.remove(Jogador);
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    public Jogador recuperaUmJogador(long id) throws ObjetoNaoEncontradoException {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    Jogador umJogador = (Jogador) em.find(Jogador.class, new Long(id));
	    

	    if (umJogador == null) {
		throw new ObjetoNaoEncontradoException();
	    }

	    return umJogador;
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    public Jogador recuperaUmJogadorComLock(long id) throws ObjetoNaoEncontradoException {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    Jogador umJogador = em.find(Jogador.class, id, LockModeType.PESSIMISTIC_WRITE);

	    if (umJogador == null) {
		throw new ObjetoNaoEncontradoException();
	    }

	    return umJogador;
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    @SuppressWarnings("unchecked")
    public List<Jogador> recuperaJogadores() {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    List<Jogador> jogadores = em.createQuery("select j from banco.jogador j " + "order by j.id asc").getResultList();

	    return jogadores;
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    public Jogador recuperaUmJogadorEPersonagens(long numero) throws ObjetoNaoEncontradoException {
	/*
	 * O que a maioria das pessoas pensam quando escutam a palavra join no contexto
	 * de bancos de dados SQL � um inner join. Um inner join � o tipo de join mais
	 * simples.
	 *
	 * Por exemplo, para se recuperar todos os Jogadors que possuem lances, �
	 * preciso utilizar um inner join. Neste caso apenas Jogadors que possuem lances
	 * s�o recuperados. Mas se desejarmos recuperar os Jogadors e valores nulos para
	 * os dados dos lances quando o Jogador n�o tiver lances, neste caso
	 * utilizaremos um left outer join. (estilo ANSI).
	 *
	 * Se fizermos a jun��o de duas tabelas Jogador e LANCE, utilizando um inner
	 * join obteremos todos os Jogadors e seus lances na tabela resultante. No caso
	 * de um "left outer join", cada linha da tabela a esquerda (left - tabela
	 * Jogador) que nunca satisfaz a condi��o de jun��o tamb�m � inclu�da no
	 * resultado com valores nulos retornados para todas as colunas da tabela LANCE.
	 * 
	 * Um "right outer join" recuperaria todos os lances com um valor nulo para o
	 * Jogador se o lance n�o tem rela��o com nenhum Jogador.
	 * 
	 * A condi��o de join deve ser especificada na cl�usula "on" para uma jun��o no
	 * estilo "ANSI" ou na cl�usula "where" para uma jun��o no estilo "theta".
	 * 
	 * Exemplo: P.ID = L.Jogador_ID.
	 *
	 * Left Outer Join no Oracle:
	 *
	 * SELECT P.ID, P.NOME, L.ID, L.VALOR FROM Jogador P, LANCE L 
	 * WHERE P.ID = L.Jogador_ID(+) ORDER BY P.ID, L.VALOR;
	 */
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    String busca = "select j from Jogador j left outer join fetch j.personagens where j.id = :id";

	    Jogador umJogador = (Jogador) em.createQuery(busca).setParameter("id", numero).getSingleResult();

	    // A busca retorna um �nico Jogador (SingleResult()).

	    /*
	     * Em fun��o do m�todo getSingleResult() ser� propagada a exce��o
	     * NoResultException caso nenhum Jogador seja encontrado.
	     */

	    return umJogador;
	} catch (NoResultException e) {
	    throw new ObjetoNaoEncontradoException();
	}
    }

    @SuppressWarnings("unchecked")
    public List<Jogador> recuperaJogadoresEPersonagens() {

	EntityManager em = JPAUtil.getEntityManager();

	List<Jogador> jogadores = em
		.createQuery("select distinct j from Jogador j left outer join fetch j.personagens")
		.getResultList();

	return jogadores;
    }
}