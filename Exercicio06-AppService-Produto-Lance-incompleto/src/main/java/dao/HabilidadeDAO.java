package dao;

import java.util.List;

import excecao.ObjetoNaoEncontradoException;
import modelo.Habilidade;

public interface HabilidadeDAO {
    long inclui(Habilidade umaHabilidade);

    void exclui(long id) throws ObjetoNaoEncontradoException;

    Habilidade recuperaUmaHabilidade(long numero) throws ObjetoNaoEncontradoException;

    List<Habilidade> recuperaHabilidades();
    
    void altera(Habilidade umaHabilidade) throws ObjetoNaoEncontradoException;

}