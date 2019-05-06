package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import dao.HabilidadeDAO;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import modelo.Habilidade;

import util.JPAUtil;

public class HabilidadeDAOImpl implements HabilidadeDAO {
	public long inclui(Habilidade umaHabilidade) {
		try {
			EntityManager em = JPAUtil.getEntityManager();

			em.persist(umaHabilidade);

			return umaHabilidade.getId();
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	public void exclui(long id) throws ObjetoNaoEncontradoException {
		try {
			EntityManager em = JPAUtil.getEntityManager();

			Habilidade lance = em.find(Habilidade.class, id, LockModeType.PESSIMISTIC_WRITE);

			if (lance == null) {
				throw new ObjetoNaoEncontradoException();
			}

			em.remove(lance);
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	public Habilidade recuperaUmaHabilidade(long id) throws ObjetoNaoEncontradoException {
		try {
			EntityManager em = JPAUtil.getEntityManager();

			Habilidade umaHabilidade = (Habilidade) em.find(Habilidade.class, new Long(id));

			if (umaHabilidade == null) {
				throw new ObjetoNaoEncontradoException();
			}

			return umaHabilidade;
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Habilidade> recuperaHabilidades() {
		EntityManager em = JPAUtil.getEntityManager();

		return em.createQuery("select h from Habilidade h order by h.id").getResultList();
	}

	
}
