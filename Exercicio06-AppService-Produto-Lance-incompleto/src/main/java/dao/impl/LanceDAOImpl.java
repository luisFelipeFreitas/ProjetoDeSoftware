package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import dao.LanceDAO;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import modelo.Lance;
import modelo.Produto;
import util.JPAUtil;

public class LanceDAOImpl implements LanceDAO {
    public long inclui(Lance umLance) {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    em.persist(umLance);

	    return umLance.getId();
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    public void exclui(long id) throws ObjetoNaoEncontradoException {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    Lance lance = em.find(Lance.class, id, LockModeType.PESSIMISTIC_WRITE);

	    if (lance == null) {
		throw new ObjetoNaoEncontradoException();
	    }

	    em.remove(lance);
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    public Lance recuperaUmLance(long id) throws ObjetoNaoEncontradoException {
	try {
	    EntityManager em = JPAUtil.getEntityManager();

	    Lance umLance = (Lance) em.find(Lance.class, new Long(id));

	    if (umLance == null) {
		throw new ObjetoNaoEncontradoException();
	    }

	    return umLance;
	} catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    @SuppressWarnings("unchecked")
    public List<Lance> recuperaLances() {
	EntityManager em = JPAUtil.getEntityManager();

	return em.createQuery("select l from Lance l order by l.id").getResultList();
    }

    @SuppressWarnings("unchecked")
    public Lance recuperaUltimoLance(Produto produto) throws ObjetoNaoEncontradoException {
	EntityManager em = JPAUtil.getEntityManager();

	String busca = "select l from Lance l " + 
	               "where l.produto.id = :idProduto " + 
		       "order by l.id desc";

	List<Lance> lances = em.createQuery(busca).setParameter("idProduto", produto.getId()).getResultList();

	if (lances.isEmpty())
	    throw new ObjetoNaoEncontradoException();
	else
	    return lances.get(0);
    }
}
