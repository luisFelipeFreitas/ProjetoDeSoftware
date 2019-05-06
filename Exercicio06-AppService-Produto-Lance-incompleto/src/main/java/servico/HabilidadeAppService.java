package servico;
import java.util.List;

import dao.HabilidadeDAO;
import dao.EquipamentoDAO;
import excecao.InfraestruturaException;
import excecao.JogadorNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;
import excecao.EquipamentoNaoEncontradoException;
import excecao.HabilidadeNaoEncontradaException;
import modelo.Habilidade;
import modelo.Jogador;
import modelo.Equipamento;
import util.FabricaDeDAOs;
import util.JPAUtil;

// @Service
public class HabilidadeAppService {
	private static HabilidadeDAO habilidadeDAO = FabricaDeDAOs.getDAO(HabilidadeDAO.class);
	private static EquipamentoDAO equipamentoDAO = FabricaDeDAOs.getDAO(EquipamentoDAO.class);

	public long inclui(Habilidade umHabilidade) throws EquipamentoNaoEncontradoException {
		// A execu��o do m�todo recuperaUmEquipamentoComLock(id) abaixo
		// impede que dois habilidades sejam cadastrados em paralelo.
		// Como este m�todo p�e um lock em Equipamento, a inser��o de
		// dois habilidades acontece sempre em s�rie, i. �, obedecendo
		// a uma fila. Isto impede que o valor do segundo habilidade seja
		// inferior ao valor do primeiro ou que se tente cadastrar um
		// habilidade para um equipamento que tenha sido removido.
		try {
			// NENHUMA VALIDA��O EST� SENDO REALIZADA AQUI!!!

			JPAUtil.beginTransaction();

			Equipamento umEquipamento = umHabilidade.getEquipamento();

			try {
				equipamentoDAO.recuperaUmEquipamentoComLock(umEquipamento.getId());
			} catch (ObjetoNaoEncontradoException e) {
				throw new EquipamentoNaoEncontradoException("Equipamento n�o encontado");
			}

			long numero = habilidadeDAO.inclui(umHabilidade);

			JPAUtil.commitTransaction();

			return numero;
		} catch (EquipamentoNaoEncontradoException e) {
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

	public void exclui(long numero) throws HabilidadeNaoEncontradaException {
		try {
			JPAUtil.beginTransaction();

			habilidadeDAO.exclui(numero);

			JPAUtil.commitTransaction();
		} catch (ObjetoNaoEncontradoException e) {
			JPAUtil.rollbackTransaction();

			throw new HabilidadeNaoEncontradaException("Habilidade n�o encontrada");
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
	public void altera(Habilidade umaHabilidade) throws HabilidadeNaoEncontradaException {
		try {
			JPAUtil.beginTransaction();

			habilidadeDAO.altera(umaHabilidade);

			JPAUtil.commitTransaction();
		} catch (ObjetoNaoEncontradoException e) {
			JPAUtil.rollbackTransaction();

			throw new HabilidadeNaoEncontradaException("Habilidade n�o encontrada");
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


	public Habilidade recuperaUmaHabilidade(long numero) throws HabilidadeNaoEncontradaException {
		try {
			Habilidade umHabilidade = habilidadeDAO.recuperaUmaHabilidade(numero);

			return umHabilidade;
		} catch (ObjetoNaoEncontradoException e) {
			throw new HabilidadeNaoEncontradaException("Habilidade n�o encontrado");
		} finally {
			JPAUtil.closeEntityManager();
		}
	}

	public List<Habilidade> recuperaHabilidades() {
		try {
			return habilidadeDAO.recuperaHabilidades();
		} finally {
			JPAUtil.closeEntityManager();
		}
	}
}