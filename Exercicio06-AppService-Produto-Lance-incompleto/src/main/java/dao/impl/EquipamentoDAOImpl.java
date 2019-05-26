package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import dao.EquipamentoDAO;
import excecao.ObjetoNaoEncontradoException;
import modelo.Equipamento;

@Repository
public class EquipamentoDAOImpl implements EquipamentoDAO {
	@PersistenceContext
	protected EntityManager em;

	public long inclui(Equipamento umEquipamento) {
		em.persist(umEquipamento);

		return umEquipamento.getId();

	}

	public void altera(Equipamento umEquipamento) throws ObjetoNaoEncontradoException {
		Equipamento equipamento = em.find(Equipamento.class, umEquipamento.getId(), LockModeType.PESSIMISTIC_WRITE);

		if (equipamento == null) {
			throw new ObjetoNaoEncontradoException();
		}

		em.merge(umEquipamento);

	}

	public void exclui(long id) throws ObjetoNaoEncontradoException {
		Equipamento equipamento = em.find(Equipamento.class, id, LockModeType.PESSIMISTIC_WRITE);

		if (equipamento == null) {
			throw new ObjetoNaoEncontradoException();
		}

		em.remove(equipamento);
	}

	public Equipamento recuperaUmEquipamento(long id) throws ObjetoNaoEncontradoException {
		Equipamento umEquipamento = (Equipamento) em.find(Equipamento.class, new Long(id));

		if (umEquipamento == null) {
			throw new ObjetoNaoEncontradoException();
		}

		return umEquipamento;

	}

	public Equipamento recuperaUmEquipamentoComLock(long id) throws ObjetoNaoEncontradoException {
		Equipamento umaRota = em.find(Equipamento.class, id, LockModeType.PESSIMISTIC_WRITE);

		if (umaRota == null) {
			throw new ObjetoNaoEncontradoException();
		}

		return umaRota;
	}

	public Equipamento recuperaUmEquipamentoEHabilidades(long numero) throws ObjetoNaoEncontradoException {
		try {
			String busca = "select e from Equipamento e left outer join fetch e.habilidades where e.id = :id";

			Equipamento umEquipamento = (Equipamento) em.createQuery(busca).setParameter("id", numero)
					.getSingleResult();

			return umEquipamento;
		} catch (NoResultException e) {
			throw new ObjetoNaoEncontradoException();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Equipamento> recuperaEquipamentosEHabilidades() {
		List<Equipamento> Equipamentos = em
				.createQuery("select distinct e from Equipamento e left outer join fetch e.habilidades")
				.getResultList();

		return Equipamentos;
	}

	@SuppressWarnings("unchecked")
	public List<Equipamento> recuperaEquipamento() {
		return em.createQuery("select e from Equipamento e order by e.id").getResultList();
	}

}
