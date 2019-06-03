package servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import annotations.Perfil;
import dao.JogadorDAO;
import excecao.JogadorNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;
import modelo.Jogador;
import servico.JogadorAppService;

public class JogadorAppServiceImpl implements JogadorAppService{
	@Autowired
	private JogadorDAO jogadorDAO ;

	@Transactional
	@Perfil(nomes={"admin", "user"})
	public long inclui(Jogador umJogador) {

		// NENHUMA VALIDAÇÃO ESTÁ SENDO REALIZADA AQUI!!!
		long numero = jogadorDAO.inclui(umJogador);
		return numero;

	}

	@Transactional
	@Perfil(nomes={"admin", "user"})
	public void altera(Jogador umJogador) throws JogadorNaoEncontradoException {
		try {

			jogadorDAO.altera(umJogador);

		} catch (ObjetoNaoEncontradoException e) {

			throw new JogadorNaoEncontradoException("Jogador não encontrado");
		}
	}

	@Transactional
	@Perfil(nomes={"admin", "user"})
	public void exclui(long numero) throws JogadorNaoEncontradoException {
		try {

			jogadorDAO.exclui(numero);

		} catch (ObjetoNaoEncontradoException e) {

			throw new JogadorNaoEncontradoException("Jogador não encontrado");
		}
	}

	public Jogador recuperaUmJogador(long numero) throws JogadorNaoEncontradoException {
		try {
			Jogador umJogador = jogadorDAO.recuperaUmJogador(numero);

			return umJogador;
		} catch (ObjetoNaoEncontradoException e) {
			throw new JogadorNaoEncontradoException("Jogador não encontrado");
		} 
	}

	public Jogador recuperaUmJogadorEPersonagens(long numero) throws JogadorNaoEncontradoException {
		try {
			return jogadorDAO.recuperaUmJogadorEPersonagens(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new JogadorNaoEncontradoException("Jogador não encontrado");
		} 
	}

	public List<Jogador> recuperaJogadoresEPersonagens() {
		
			return jogadorDAO.recuperaJogadoresEPersonagens();
		
	}
}