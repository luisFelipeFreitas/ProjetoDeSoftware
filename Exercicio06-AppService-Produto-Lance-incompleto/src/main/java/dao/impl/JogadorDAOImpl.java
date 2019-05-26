package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import dao.JogadorDAO;
import excecao.ObjetoNaoEncontradoException;
import modelo.Jogador;

@Repository
public class JogadorDAOImpl implements JogadorDAO {
	@PersistenceContext
	protected EntityManager em;

	public long inclui(Jogador umJogador) {

		em.persist(umJogador);

		return umJogador.getId();

	}

	public void altera(Jogador umJogador) throws ObjetoNaoEncontradoException {

		Jogador Jogador = em.find(Jogador.class, umJogador.getId(), LockModeType.PESSIMISTIC_WRITE);

		if (Jogador == null) {
			throw new ObjetoNaoEncontradoException();
		}

		em.merge(umJogador);

	}

	public void exclui(long id) throws ObjetoNaoEncontradoException {

		Jogador Jogador = em.find(Jogador.class, id, LockModeType.PESSIMISTIC_WRITE);

		if (Jogador == null) {
			throw new ObjetoNaoEncontradoException();
		}

		em.remove(Jogador);

	}

	public Jogador recuperaUmJogador(long id) throws ObjetoNaoEncontradoException {

		Jogador umJogador = (Jogador) em.find(Jogador.class, new Long(id));

		if (umJogador == null) {
			throw new ObjetoNaoEncontradoException();
		}

		return umJogador;

	}

	public Jogador recuperaUmJogadorComLock(long id) throws ObjetoNaoEncontradoException {

		Jogador umJogador = em.find(Jogador.class, id, LockModeType.PESSIMISTIC_WRITE);

		if (umJogador == null) {
			throw new ObjetoNaoEncontradoException();
		}

		return umJogador;

	}

	@SuppressWarnings("unchecked")
	public List<Jogador> recuperaJogadores() {

		List<Jogador> jogadores = em.createQuery("select j from banco.jogador j " + "order by j.id asc")
				.getResultList();

		return jogadores;

	}

	public Jogador recuperaUmJogadorEPersonagens(long numero) throws ObjetoNaoEncontradoException {

		String busca = "select j from Jogador j left outer join fetch j.personagens where j.id = :id";

		Jogador umJogador = (Jogador) em.createQuery(busca).setParameter("id", numero).getSingleResult();

		// A busca retorna um único Jogador (SingleResult()).

		/*
		 * Em função do método getSingleResult() será propagada a exceção
		 * NoResultException caso nenhum Jogador seja encontrado.
		 */

		return umJogador;

	}

	@SuppressWarnings("unchecked")
	public List<Jogador> recuperaJogadoresEPersonagens() {

		List<Jogador> jogadores = em.createQuery("select distinct j from Jogador j left outer join fetch j.personagens")
				.getResultList();

		return jogadores;
	}
}