package dao;

import java.util.List;

import modelo.Habilidade;
import modelo.Produto;
import excecao.ObjetoNaoEncontradoException;

public interface HabilidadeDAO {
    long inclui(Habilidade umaHabilidade);

    void exclui(long id) throws ObjetoNaoEncontradoException;

    Habilidade recuperaUmaHabilidade(long numero) throws ObjetoNaoEncontradoException;

    List<Habilidade> recuperaHabilidades();

}