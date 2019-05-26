package servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dao.JogadorDAO;
import dao.PersonagemDAO;
import excecao.JogadorNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;
import excecao.PersonagemNaoEncontradaException;
import modelo.Jogador;
import modelo.Personagem;
import service.PersonagemAppService;

public class PersonagemAppServiceImpl implements PersonagemAppService {
	@Autowired
	private PersonagemDAO personagemDAO;
	@Autowired
	private JogadorDAO jogadorDAO ;

	@Transactional
	public long inclui(Personagem umaPersonagem) throws JogadorNaoEncontradoException {

		try {
			// NENHUMA VALIDA��O EST� SENDO REALIZADA AQUI!!!

			Jogador umaJogador = umaPersonagem.getJogador();

			try {
				jogadorDAO.recuperaUmJogadorComLock(umaJogador.getId());
			} catch (ObjetoNaoEncontradoException e) {
				throw new JogadorNaoEncontradoException("Jogador n�o encontado");
			}

			long numero = personagemDAO.inclui(umaPersonagem);

			return numero;
		} catch (JogadorNaoEncontradoException e) {

			throw e;
		}
	}

	@Transactional
	public void altera(Personagem umaPersonagem) throws PersonagemNaoEncontradaException {
		try {

			personagemDAO.altera(umaPersonagem);

		} catch (ObjetoNaoEncontradoException e) {

			throw new PersonagemNaoEncontradaException("Personagem n�o encontrada");
		}
	}

	@Transactional
	public void exclui(long numero) throws PersonagemNaoEncontradaException {
		try {

			personagemDAO.exclui(numero);

		} catch (ObjetoNaoEncontradoException e) {
			throw new PersonagemNaoEncontradaException("Personagem n�o encontrado");
		}
	}

	public Personagem recuperaUmaPersonagem(long numero) throws PersonagemNaoEncontradaException {
		try {
			Personagem umaPersonagem = personagemDAO.recuperaUmaPersonagem(numero);

			return umaPersonagem;
		} catch (ObjetoNaoEncontradoException e) {
			throw new PersonagemNaoEncontradaException("Personagem n�o encontrado");
		} 
	}

	public Personagem recuperaUmaPersonagemEEquipamentos(long numero) throws PersonagemNaoEncontradaException {
		try {
			return personagemDAO.recuperaUmaPersonagemEEquipamentos(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new PersonagemNaoEncontradaException("Personagem n�o encontrada");
		} 
	}

	public List<Personagem> recuperaPersonagensEEquipamentos() {
		
			return personagemDAO.recuperaPersonagensEEquipamentos();
		 
	}
}