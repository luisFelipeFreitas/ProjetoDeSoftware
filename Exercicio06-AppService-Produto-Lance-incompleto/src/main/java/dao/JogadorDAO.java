package dao;

import java.util.List;

import modelo.Jogador;
import excecao.ObjetoNaoEncontradoException;

public interface JogadorDAO {
    long inclui(Jogador umJogador);

    void altera(Jogador umJogador) throws ObjetoNaoEncontradoException;

    void exclui(long id) throws ObjetoNaoEncontradoException;

    Jogador recuperaUmJogador(long numero) throws ObjetoNaoEncontradoException;

    Jogador recuperaUmJogadorComLock(long numero) throws ObjetoNaoEncontradoException;

    Jogador recuperaUmJogadorEPersonagens(long numero) throws ObjetoNaoEncontradoException;

    List<Jogador> recuperaJogadoresEPersonagens();
}