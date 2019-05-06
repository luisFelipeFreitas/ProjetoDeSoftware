package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;

import dao.EquipamentoDAO;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import modelo.Equipamento;
import util.JPAUtil;

public class EquipamentoDAOImpl implements EquipamentoDAO {
    public long inclui(Equipamento umEquipamento) {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    em.persist(umEquipamento);

	    return umEquipamento.getId();
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }
    
    public void altera(Equipamento umEquipamento) throws ObjetoNaoEncontradoException {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    Equipamento equipamento = em.find(Equipamento.class, umEquipamento.getId(), LockModeType.PESSIMISTIC_WRITE);

	    if (equipamento == null) {
		throw new ObjetoNaoEncontradoException();
	    }

	    em.merge(umEquipamento);
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    public void exclui(long id) throws ObjetoNaoEncontradoException {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    Equipamento equipamento = em.find(Equipamento.class, id, LockModeType.PESSIMISTIC_WRITE);

	    if (equipamento == null) {
		throw new ObjetoNaoEncontradoException();
	    }

	    em.remove(equipamento);
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    public Equipamento recuperaUmEquipamento(long id) throws ObjetoNaoEncontradoException {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    Equipamento umEquipamento = (Equipamento) em.find(Equipamento.class, new Long(id));

	    if (umEquipamento == null) {
		throw new ObjetoNaoEncontradoException();
	    }

	    return umEquipamento;
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }
    
    public Equipamento recuperaUmEquipamentoComLock(long id) throws ObjetoNaoEncontradoException {
    	try {
    	    EntityManager em = JPAUtil.getEntityManager();

    	    Equipamento umaRota = em.find(Equipamento.class, id, LockModeType.PESSIMISTIC_WRITE);

    	    if (umaRota == null) {
    		throw new ObjetoNaoEncontradoException();
    	    }

    	    return umaRota;
    	} catch (RuntimeException e) {
    	    throw new InfraestruturaException(e);
    	}
        }
    
    public Equipamento recuperaUmEquipamentoEHabilidades(long numero) throws ObjetoNaoEncontradoException {
    	try {
    	    EntityManager em = JPAUtil.getEntityManager();

    	    String busca = "select e from Equipamento e left outer join fetch e.habilidades where e.id = :id";

    	    Equipamento umEquipamento = (Equipamento) em.createQuery(busca).setParameter("id", numero).getSingleResult();

    	    return umEquipamento;
    	} catch (NoResultException e) {
    	    throw new ObjetoNaoEncontradoException();
    	}
        }
    
    @SuppressWarnings("unchecked")
    public List<Equipamento> recuperaEquipamentosEHabilidades() {

	EntityManager em = JPAUtil.getEntityManager();

	List<Equipamento> Equipamentos = em
		.createQuery("select distinct e from Equipamento e left outer join fetch e.habilidades")
		.getResultList();

	return Equipamentos;
    }
	
    

    @SuppressWarnings("unchecked")
    public List<Equipamento> recuperaEquipamento() {
	EntityManager em = JPAUtil.getEntityManager();

	return em.createQuery("select e from Equipamento e order by e.id").getResultList();
    }


}
