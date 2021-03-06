package dao;

import java.util.List;

import modelo.Equipamento;
import excecao.ObjetoNaoEncontradoException;

public interface EquipamentoDAO {
    long inclui(Equipamento umEquipamento);

    void altera(Equipamento umEquipamento) throws ObjetoNaoEncontradoException;

    void exclui(long id) throws ObjetoNaoEncontradoException;

    Equipamento recuperaUmEquipamento(long numero) throws ObjetoNaoEncontradoException;

    Equipamento recuperaUmEquipamentoComLock(long numero) throws ObjetoNaoEncontradoException;

    Equipamento recuperaUmEquipamentoEHabilidades(long numero) throws ObjetoNaoEncontradoException;

    List<Equipamento> recuperaEquipamentosEHabilidades();
}