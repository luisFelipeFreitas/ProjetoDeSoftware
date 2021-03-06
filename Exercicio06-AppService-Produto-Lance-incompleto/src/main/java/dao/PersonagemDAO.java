package dao;

import java.util.List;

import modelo.Personagem;
import excecao.ObjetoNaoEncontradoException;

public interface PersonagemDAO {
    long inclui(Personagem umaPersonagem);

    void altera(Personagem umaPersonagem) throws ObjetoNaoEncontradoException;

    void exclui(long id) throws ObjetoNaoEncontradoException;

    Personagem recuperaUmaPersonagem(long numero) throws ObjetoNaoEncontradoException;

    Personagem recuperaUmaPersonagemComLock(long numero) throws ObjetoNaoEncontradoException;

    Personagem recuperaUmaPersonagemEEquipamentos(long numero) throws ObjetoNaoEncontradoException;

    List<Personagem> recuperaPersonagensEEquipamentos();
}