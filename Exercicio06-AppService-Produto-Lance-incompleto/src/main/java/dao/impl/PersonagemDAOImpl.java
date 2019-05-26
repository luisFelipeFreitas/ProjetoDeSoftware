package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import dao.PersonagemDAO;
import excecao.ObjetoNaoEncontradoException;
import modelo.Personagem;

@Repository
public class PersonagemDAOImpl implements PersonagemDAO {
	@PersistenceContext
	protected EntityManager em;

	public long inclui(Personagem umaPersonagem) {

		em.persist(umaPersonagem);

		return umaPersonagem.getId();
	}

	public void altera(Personagem umaPersonagem) throws ObjetoNaoEncontradoException {
			Personagem personagem = em.find(Personagem.class, umaPersonagem.getId(), LockModeType.PESSIMISTIC_WRITE);

			if (personagem == null) {
				throw new ObjetoNaoEncontradoException();
			}
			em.merge(umaPersonagem);
		
	}

	public void exclui(long id) throws ObjetoNaoEncontradoException {
		
			Personagem personagem = em.find(Personagem.class, id, LockModeType.PESSIMISTIC_WRITE);

			if (personagem == null) {
				throw new ObjetoNaoEncontradoException();
			}

			em.remove(personagem);
		
	}

	public Personagem recuperaUmaPersonagem(long id) throws ObjetoNaoEncontradoException {
		
			Personagem umaPersonagem = (Personagem) em.find(Personagem.class, new Long(id));

			if (umaPersonagem == null) {
				throw new ObjetoNaoEncontradoException();
			}

			return umaPersonagem;
		
	}

	public Personagem recuperaUmaPersonagemComLock(long id) throws ObjetoNaoEncontradoException {
		
			Personagem umaRota = em.find(Personagem.class, id, LockModeType.PESSIMISTIC_WRITE);

			if (umaRota == null) {
				throw new ObjetoNaoEncontradoException();
			}

			return umaRota;
		
	}

	public Personagem recuperaUmaPersonagemEEquipamentos(long numero) throws ObjetoNaoEncontradoException {
		try {
			String busca = "select p from Personagem p left outer join fetch p.equipamentos where p.id = :id";

			Personagem umaPersonagem = (Personagem) em.createQuery(busca).setParameter("id", numero).getSingleResult();

			return umaPersonagem;
		} catch (NoResultException e) {
			throw new ObjetoNaoEncontradoException();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Personagem> recuperaPersonagensEEquipamentos() {
		List<Personagem> Personagems = em
				.createQuery("select distinct p from Personagem p left outer join fetch p.equipamentos")
				.getResultList();

		return Personagems;
	}

	@SuppressWarnings("unchecked")
	public List<Personagem> recuperaPersonagem() {
		return em.createQuery("select p from Personagem p order by p.id").getResultList();
	}

}
