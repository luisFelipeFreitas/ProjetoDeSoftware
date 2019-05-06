package servico;


import java.util.List;

import dao.EquipamentoDAO;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import excecao.EquipamentoNaoEncontradoException;
import excecao.PersonagemNaoEncontradaException;
import modelo.Equipamento;
import modelo.Personagem;
import util.FabricaDeDAOs;
import util.JPAUtil;
import dao.PersonagemDAO;

public class EquipamentoAppService {
    private static EquipamentoDAO equipamentoDAO = FabricaDeDAOs.getDAO(EquipamentoDAO.class);
    private static PersonagemDAO personagemDAO = FabricaDeDAOs.getDAO(PersonagemDAO.class);

    public long inclui(Equipamento umEquipamento)
    	    throws PersonagemNaoEncontradaException {

    	try {
    	    // NENHUMA VALIDA��O EST� SENDO REALIZADA AQUI!!!

    	    JPAUtil.beginTransaction();

    	    Personagem umaPersonagem = umEquipamento.getPersonagem();

    	    try {
    		personagemDAO.recuperaUmaPersonagemComLock(umaPersonagem.getId());
    	    } catch (ObjetoNaoEncontradoException e) {
    		throw new PersonagemNaoEncontradaException("Personagem n�o encontado");
    	    }

    	    
    	    long numero = equipamentoDAO.inclui(umEquipamento);

    	    JPAUtil.commitTransaction();

    	    return numero;
    	} catch (PersonagemNaoEncontradaException e) {
    	    try {
    		JPAUtil.rollbackTransaction();
    	    } catch (InfraestruturaException ie) {
    	    }

    	    throw e;
    	} catch (InfraestruturaException e) {
    	    try {
    		JPAUtil.rollbackTransaction();
    	    } catch (InfraestruturaException ie) {
    	    }

    	    throw e;
    	} finally {
    	    JPAUtil.closeEntityManager();
    	}
        }

    public void altera(Equipamento umEquipamento) throws EquipamentoNaoEncontradoException {
	try {
	    JPAUtil.beginTransaction();

	    equipamentoDAO.altera(umEquipamento);

	    JPAUtil.commitTransaction();
	} catch (ObjetoNaoEncontradoException e) {
	    JPAUtil.rollbackTransaction();

	    throw new EquipamentoNaoEncontradoException("Equipamento n�o encontrado");
	} catch (InfraestruturaException e) {
	    try {
		JPAUtil.rollbackTransaction();
	    } catch (InfraestruturaException ie) {
	    }

	    throw e;
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public void exclui(long numero) throws EquipamentoNaoEncontradoException {
	try {
	    JPAUtil.beginTransaction();

	    equipamentoDAO.exclui(numero);

	    JPAUtil.commitTransaction();
	} catch (ObjetoNaoEncontradoException e) {
	    JPAUtil.rollbackTransaction();

	    throw new EquipamentoNaoEncontradoException("Equipamento n�o encontrado");
	} catch (InfraestruturaException e) {
	    try {
		JPAUtil.rollbackTransaction();
	    } catch (InfraestruturaException ie) {
	    }

	    throw e;
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public Equipamento recuperaUmEquipamento(long numero) throws EquipamentoNaoEncontradoException {
	try {
	    Equipamento umEquipamento = equipamentoDAO.recuperaUmEquipamento(numero);

	    return umEquipamento;
	} catch (ObjetoNaoEncontradoException e) {
	    throw new EquipamentoNaoEncontradoException("Equipamento n�o encontrado");
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public Equipamento recuperaUmEquipamentoEHabilidades(long numero) throws EquipamentoNaoEncontradoException {
	try {
	    return equipamentoDAO.recuperaUmEquipamentoEHabilidades(numero);
	}
	catch (ObjetoNaoEncontradoException e) {
	    throw new EquipamentoNaoEncontradoException("Equipamento n�o encontrado");
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public List<Equipamento> recuperaEquipamentosEHabilidades() {
	try {
	    return equipamentoDAO.recuperaEquipamentosEHabilidades();
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }
}