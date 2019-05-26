package servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dao.EquipamentoDAO;
import dao.HabilidadeDAO;
import excecao.EquipamentoNaoEncontradoException;
import excecao.HabilidadeNaoEncontradaException;
import excecao.ObjetoNaoEncontradoException;
import modelo.Equipamento;
import modelo.Habilidade;
import service.HabilidadeAppService;

// @Service
public class HabilidadeAppServiceImpl implements HabilidadeAppService {
	@Autowired
	private HabilidadeDAO habilidadeDAO;
	@Autowired
	private EquipamentoDAO equipamentoDAO;

	@Transactional
	public long inclui(Habilidade umHabilidade) throws EquipamentoNaoEncontradoException {

		try {
			// NENHUMA VALIDAÇÃO ESTÁ SENDO REALIZADA AQUI!!!
			Equipamento umEquipamento = umHabilidade.getEquipamento();

			try {
				equipamentoDAO.recuperaUmEquipamentoComLock(umEquipamento.getId());
			} catch (ObjetoNaoEncontradoException e) {
				throw new EquipamentoNaoEncontradoException("Equipamento não encontado");
			}

			long numero = habilidadeDAO.inclui(umHabilidade);
			return numero;
		} catch (EquipamentoNaoEncontradoException e) {

			throw e;
		}
	}

	public void exclui(long numero) throws HabilidadeNaoEncontradaException {
		try {

			habilidadeDAO.exclui(numero);

		} catch (ObjetoNaoEncontradoException e) {

			throw new HabilidadeNaoEncontradaException("Habilidade não encontrada");
		}
	}

	public void altera(Habilidade umaHabilidade) throws HabilidadeNaoEncontradaException {
		try {
			habilidadeDAO.altera(umaHabilidade);
		} catch (ObjetoNaoEncontradoException e) {
			throw new HabilidadeNaoEncontradaException("Habilidade não encontrada");
		}
	}

	public Habilidade recuperaUmaHabilidade(long numero) throws HabilidadeNaoEncontradaException {
		try {
			Habilidade umHabilidade = habilidadeDAO.recuperaUmaHabilidade(numero);

			return umHabilidade;
		} catch (ObjetoNaoEncontradoException e) {
			throw new HabilidadeNaoEncontradaException("Habilidade não encontrado");
		}
	}

	public List<Habilidade> recuperaHabilidades() {

		return habilidadeDAO.recuperaHabilidades();

	}
}