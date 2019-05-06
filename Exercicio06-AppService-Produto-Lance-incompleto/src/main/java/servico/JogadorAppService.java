package servico;

import java.util.List;

import dao.JogadorDAO;
import excecao.InfraestruturaException;
import excecao.JogadorNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;
import modelo.Jogador;
import util.FabricaDeDAOs;
import util.JPAUtil;

public class JogadorAppService {
	private static JogadorDAO jogadorDAO = FabricaDeDAOs.getDAO(JogadorDAO.class);

	public long inclui(Jogador umJogador) {
		try {
			// NENHUMA VALIDAÇÃO ESTÁ SENDO REALIZADA AQUI!!!

			JPAUtil.beginTransaction();

			long numero = jogadorDAO.inclui(umJogador);

			JPAUtil.commitTransaction();

			return numero;
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

	public void altera(Jogador umJogador) throws JogadorNaoEncontradoException {
		try {
			JPAUtil.beginTransaction();

			jogadorDAO.altera(umJogador);

			JPAUtil.commitTransaction();
		} catch (ObjetoNaoEncontradoException e) {
			JPAUtil.rollbackTransaction();

			throw new JogadorNaoEncontradoException("Jogador não encontrado");
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

	public void exclui(long numero) throws JogadorNaoEncontradoException {
		try {
			JPAUtil.beginTransaction();

			jogadorDAO.exclui(numero);

			JPAUtil.commitTransaction();
		} catch (ObjetoNaoEncontradoException e) {
			JPAUtil.rollbackTransaction();

			throw new JogadorNaoEncontradoException("Jogador não encontrado");
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

	public Jogador recuperaUmJogador(long numero) throws JogadorNaoEncontradoException {
		try {
			Jogador umJogador = jogadorDAO.recuperaUmJogador(numero);

			return umJogador;
		} catch (ObjetoNaoEncontradoException e) {
			throw new JogadorNaoEncontradoException("Jogador não encontrado");
		} finally {
			JPAUtil.closeEntityManager();
		}
	}

	public Jogador recuperaUmJogadorEPersonagens(long numero) throws JogadorNaoEncontradoException {
		try {
			return jogadorDAO.recuperaUmJogadorEPersonagens(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new JogadorNaoEncontradoException("Jogador não encontrado");
		} finally {
			JPAUtil.closeEntityManager();
		}
	}

	public List<Jogador> recuperaJogadoresEPersonagens() {
		try {
			return jogadorDAO.recuperaJogadoresEPersonagens();
		} finally {
			JPAUtil.closeEntityManager();
		}
	}
}