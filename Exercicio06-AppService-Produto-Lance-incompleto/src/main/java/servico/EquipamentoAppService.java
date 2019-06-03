package servico;

import java.util.List;

import excecao.EquipamentoNaoEncontradoException;
import excecao.PersonagemNaoEncontradaException;
import modelo.Equipamento;

public interface EquipamentoAppService {
    long inclui(Equipamento umEquipamento) throws PersonagemNaoEncontradaException;

    void altera(Equipamento umEquipamento) throws EquipamentoNaoEncontradoException;

    void exclui(long numero) throws EquipamentoNaoEncontradoException;

    Equipamento recuperaUmEquipamentoEHabilidades(long numero) throws EquipamentoNaoEncontradoException;
    
    Equipamento recuperaUmEquipamento(long numero) throws EquipamentoNaoEncontradoException;

    List<Equipamento> recuperaEquipamentosEHabilidades();
}