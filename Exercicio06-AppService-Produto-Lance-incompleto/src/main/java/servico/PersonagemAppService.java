package servico;


import java.util.List;

import dao.PersonagemDAO;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import excecao.PersonagemNaoEncontradaException;
import excecao.JogadorNaoEncontradoException;
import modelo.Personagem;
import modelo.Jogador;
import util.FabricaDeDAOs;
import util.JPAUtil;
import dao.JogadorDAO;

public class PersonagemAppService {
    private static PersonagemDAO personagemDAO = FabricaDeDAOs.getDAO(PersonagemDAO.class);
    private static JogadorDAO jogadorDAO = FabricaDeDAOs.getDAO(JogadorDAO.class);

    public long inclui(Personagem umaPersonagem)
    	    throws JogadorNaoEncontradoException {

    	try {
    	    // NENHUMA VALIDA��O EST� SENDO REALIZADA AQUI!!!

    	    JPAUtil.beginTransaction();

    	    Jogador umaJogador = umaPersonagem.getJogador();

    	    try {
    		jogadorDAO.recuperaUmJogadorComLock(umaJogador.getId());
    	    } catch (ObjetoNaoEncontradoException e) {
    		throw new JogadorNaoEncontradoException("Jogador n�o encontado");
    	    }

    	    
    	    long numero = personagemDAO.inclui(umaPersonagem);

    	    JPAUtil.commitTransaction();

    	    return numero;
    	} catch (JogadorNaoEncontradoException e) {
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

    public void altera(Personagem umaPersonagem) throws PersonagemNaoEncontradaException {
	try {
	    JPAUtil.beginTransaction();

	    personagemDAO.altera(umaPersonagem);

	    JPAUtil.commitTransaction();
	} catch (ObjetoNaoEncontradoException e) {
	    JPAUtil.rollbackTransaction();

	    throw new PersonagemNaoEncontradaException("Personagem n�o encontrada");
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

    public void exclui(long numero) throws PersonagemNaoEncontradaException {
	try {
	    JPAUtil.beginTransaction();

	    personagemDAO.exclui(numero);

	    JPAUtil.commitTransaction();
	} catch (ObjetoNaoEncontradoException e) {
	    JPAUtil.rollbackTransaction();

	    throw new PersonagemNaoEncontradaException("Personagem n�o encontrado");
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

    public Personagem recuperaUmaPersonagem(long numero) throws PersonagemNaoEncontradaException {
	try {
	    Personagem umaPersonagem = personagemDAO.recuperaUmaPersonagem(numero);

	    return umaPersonagem;
	} catch (ObjetoNaoEncontradoException e) {
	    throw new PersonagemNaoEncontradaException("Personagem n�o encontrado");
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public Personagem recuperaUmaPersonagemEEquipamentos(long numero) throws PersonagemNaoEncontradaException {
	try {
	    return personagemDAO.recuperaUmaPersonagemEEquipamentos(numero);
	}
	catch (ObjetoNaoEncontradoException e) {
	    throw new PersonagemNaoEncontradaException("Personagem n�o encontrada");
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public List<Personagem> recuperaPersonagensEEquipamentos() {
	try {
	    return personagemDAO.recuperaPersonagensEEquipamentos();
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }
}