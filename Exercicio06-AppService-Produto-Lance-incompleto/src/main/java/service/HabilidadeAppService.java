package service;

import java.util.List;

import excecao.EquipamentoNaoEncontradoException;
import excecao.HabilidadeNaoEncontradaException;
import modelo.Habilidade;

public interface HabilidadeAppService {
    long inclui(Habilidade umaHabilidade) throws EquipamentoNaoEncontradoException;

    void altera(Habilidade umaHabilidade) throws HabilidadeNaoEncontradaException;

    void exclui(long numero) throws HabilidadeNaoEncontradaException;

    
    Habilidade recuperaUmaHabilidade(long numaero) throws HabilidadeNaoEncontradaException;

    List<Habilidade> recuperaHabilidades();
}