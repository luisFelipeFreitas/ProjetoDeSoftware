package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;

import dao.PersonagemDAO;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import modelo.Personagem;
import util.JPAUtil;

public class PersonagemDAOImpl implements PersonagemDAO {
    public long inclui(Personagem umaPersonagem) {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    em.persist(umaPersonagem);

	    return umaPersonagem.getId();
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }
    
    public void altera(Personagem umaPersonagem) throws ObjetoNaoEncontradoException {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    Personagem personagem = em.find(Personagem.class, umaPersonagem.getId(), LockModeType.PESSIMISTIC_WRITE);

	    if (personagem == null) {
		throw new ObjetoNaoEncontradoException();
	    }

	    em.merge(umaPersonagem);
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    public void exclui(long id) throws ObjetoNaoEncontradoException {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    Personagem personagem = em.find(Personagem.class, id, LockModeType.PESSIMISTIC_WRITE);

	    if (personagem == null) {
		throw new ObjetoNaoEncontradoException();
	    }

	    em.remove(personagem);
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    public Personagem recuperaUmaPersonagem(long id) throws ObjetoNaoEncontradoException {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    Personagem umaPersonagem = (Personagem) em.find(Personagem.class, new Long(id));

	    if (umaPersonagem == null) {
		throw new ObjetoNaoEncontradoException();
	    }

	    return umaPersonagem;
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }
    
    public Personagem recuperaUmaPersonagemComLock(long id) throws ObjetoNaoEncontradoException {
    	try {
    	    EntityManager em = JPAUtil.getEntityManager();

    	    Personagem umaRota = em.find(Personagem.class, id, LockModeType.PESSIMISTIC_WRITE);

    	    if (umaRota == null) {
    		throw new ObjetoNaoEncontradoException();
    	    }

    	    return umaRota;
    	} catch (RuntimeException e) {
    	    throw new InfraestruturaException(e);
    	}
        }
    
    public Personagem recuperaUmaPersonagemEEquipamentos(long numero) throws ObjetoNaoEncontradoException {
    	try {
    	    EntityManager em = JPAUtil.getEntityManager();

    	    String busca = "select p from Personagem p left outer join fetch p.equipamentos where p.id = :id";

    	    Personagem umaPersonagem = (Personagem) em.createQuery(busca).setParameter("id", numero).getSingleResult();

    	    return umaPersonagem;
    	} catch (NoResultException e) {
    	    throw new ObjetoNaoEncontradoException();
    	}
        }
    
    @SuppressWarnings("unchecked")
    public List<Personagem> recuperaPersonagensEEquipamentos() {

	EntityManager em = JPAUtil.getEntityManager();

	List<Personagem> Personagems = em
		.createQuery("select distinct p from Personagem p left outer join fetch p.equipamentos")
		.getResultList();

	return Personagems;
    }
	
    

    @SuppressWarnings("unchecked")
    public List<Personagem> recuperaPersonagem() {
	EntityManager em = JPAUtil.getEntityManager();

	return em.createQuery("select p from Personagem p order by p.id").getResultList();
    }


}
