package service;

import java.util.List;

import excecao.JogadorNaoEncontradoException;
import modelo.Jogador;

public interface JogadorAppService {
    long inclui(Jogador umJogador);

    void altera(Jogador umJogador) throws JogadorNaoEncontradoException;

    void exclui(long numero) throws JogadorNaoEncontradoException;

    Jogador recuperaUmJogadorEPersonagens(long numero) throws JogadorNaoEncontradoException;
    
    Jogador recuperaUmJogador(long numero) throws JogadorNaoEncontradoException;

    List<Jogador> recuperaJogadoresEPersonagens();
}