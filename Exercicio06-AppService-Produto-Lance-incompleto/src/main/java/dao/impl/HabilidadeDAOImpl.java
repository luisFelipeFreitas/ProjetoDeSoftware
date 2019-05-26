package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import dao.HabilidadeDAO;
import excecao.ObjetoNaoEncontradoException;
import modelo.Habilidade;
import util.JPAUtil;

@Repository
public class HabilidadeDAOImpl implements HabilidadeDAO {
	@PersistenceContext
	protected EntityManager em;

	public long inclui(Habilidade umaHabilidade) {
		em.persist(umaHabilidade);

		return umaHabilidade.getId();
	}

	public void exclui(long id) throws ObjetoNaoEncontradoException {
		Habilidade habilidade = em.find(Habilidade.class, id, LockModeType.PESSIMISTIC_WRITE);

		if (habilidade == null) {
			throw new ObjetoNaoEncontradoException();
		}

		em.remove(habilidade);
	}

	public void altera(Habilidade umaHabilidade) throws ObjetoNaoEncontradoException {

		Habilidade habilidade = em.find(Habilidade.class, umaHabilidade.getId(), LockModeType.PESSIMISTIC_WRITE);
		if (habilidade == null) {
			throw new ObjetoNaoEncontradoException();
		}

		em.merge(umaHabilidade);

	}

	public Habilidade recuperaUmaHabilidade(long id) throws ObjetoNaoEncontradoException {

		EntityManager em = JPAUtil.getEntityManager();

		Habilidade umaHabilidade = (Habilidade) em.find(Habilidade.class, new Long(id));

		if (umaHabilidade == null) {
			throw new ObjetoNaoEncontradoException();
		}

		return umaHabilidade;

	}

	@SuppressWarnings("unchecked")
	public List<Habilidade> recuperaHabilidades() {
		return em.createQuery("select h from Habilidade h order by h.id").getResultList();
	}

}
