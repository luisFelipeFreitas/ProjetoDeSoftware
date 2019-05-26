package servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dao.EquipamentoDAO;
import dao.PersonagemDAO;
import excecao.EquipamentoNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;
import excecao.PersonagemNaoEncontradaException;
import modelo.Equipamento;
import modelo.Personagem;
import service.EquipamentoAppService;
import util.FabricaDeDAOs;

public class EquipamentoAppServiceImpl implements EquipamentoAppService {
	@Autowired
	private static EquipamentoDAO equipamentoDAO = FabricaDeDAOs.getDAO(EquipamentoDAO.class);
	@Autowired
	private static PersonagemDAO personagemDAO = FabricaDeDAOs.getDAO(PersonagemDAO.class);

	@Transactional
	public long inclui(Equipamento umEquipamento) throws PersonagemNaoEncontradaException {

		try {
			// NENHUMA VALIDAÇÃO ESTÁ SENDO REALIZADA AQUI!!!

			Personagem umaPersonagem = umEquipamento.getPersonagem();

			try {
				personagemDAO.recuperaUmaPersonagemComLock(umaPersonagem.getId());
			} catch (ObjetoNaoEncontradoException e) {
				throw new PersonagemNaoEncontradaException("Personagem não encontado");
			}

			long numero = equipamentoDAO.inclui(umEquipamento);

			return numero;
		} catch (PersonagemNaoEncontradaException e) {

			throw e;
		}
	}

	@Transactional
	public void altera(Equipamento umEquipamento) throws EquipamentoNaoEncontradoException {
		try {
			equipamentoDAO.altera(umEquipamento);

		} catch (ObjetoNaoEncontradoException e) {

			throw new EquipamentoNaoEncontradoException("Equipamento não encontrado");
		}
	}

	@Transactional
	public void exclui(long numero) throws EquipamentoNaoEncontradoException {
		try {
			equipamentoDAO.exclui(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new EquipamentoNaoEncontradoException("Equipamento não encontrado");
		}
	}

	public Equipamento recuperaUmEquipamento(long numero) throws EquipamentoNaoEncontradoException {
		try {
			Equipamento umEquipamento = equipamentoDAO.recuperaUmEquipamento(numero);

			return umEquipamento;
		} catch (ObjetoNaoEncontradoException e) {
			throw new EquipamentoNaoEncontradoException("Equipamento não encontrado");
		}
	}

	public Equipamento recuperaUmEquipamentoEHabilidades(long numero) throws EquipamentoNaoEncontradoException {
		try {
			return equipamentoDAO.recuperaUmEquipamentoEHabilidades(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new EquipamentoNaoEncontradoException("Equipamento não encontrado");
		}
	}

	public List<Equipamento> recuperaEquipamentosEHabilidades() {

		return equipamentoDAO.recuperaEquipamentosEHabilidades();
	}
}