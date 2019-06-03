package servico;

import java.util.List;

import excecao.JogadorNaoEncontradoException;
import excecao.PersonagemNaoEncontradaException;
import modelo.Personagem;

public interface PersonagemAppService {
    long inclui(Personagem umaPersonagem) throws JogadorNaoEncontradoException;

    void altera(Personagem umaPersonagem) throws PersonagemNaoEncontradaException;

    void exclui(long numero) throws PersonagemNaoEncontradaException;

    Personagem recuperaUmaPersonagemEEquipamentos(long numero) throws PersonagemNaoEncontradaException;
    
    Personagem recuperaUmaPersonagem(long numero) throws PersonagemNaoEncontradaException;

    List<Personagem> recuperaPersonagensEEquipamentos();
}