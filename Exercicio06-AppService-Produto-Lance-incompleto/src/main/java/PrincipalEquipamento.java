import java.util.List;

import corejava.Console;
import excecao.PersonagemNaoEncontradaException;
import excecao.EquipamentoNaoEncontradoException;
import modelo.Equipamento;
import modelo.Personagem;
import servico.PersonagemAppService;
import servico.EquipamentoAppService;
import util.Util;
import modelo.Habilidade;

public class PrincipalEquipamento {
    public static void main(String[] args) {
	String nome;
	String tipo;
	String elemento;

	Equipamento umEquipamento;
	Personagem umaPersonagem;

	EquipamentoAppService equipamentoAppService = new EquipamentoAppService();
	PersonagemAppService personagemAppService = new PersonagemAppService();

	boolean continua = true;
	while (continua) {
	    System.out.println('\n' + "O que voc� deseja fazer?");
	    /* ==> */ System.out.println('\n' + "1. Cadastrar um equipamento de uma personagem");
	    /* ==> */ System.out.println("2. Remover um equipamento");
	    System.out.println("3. Recuperar um equipamento e todas as suas habilidades");
	    System.out.println("4. Recuperar todos os equipamentos e suas habilidades");
	    System.out.println("5. Sair");

	    int opcao = Console.readInt('\n' + "Digite um n�mero entre 1 e 5:");

	    switch (opcao) {
	    case 1: {
		long idPersonagem = Console.readInt('\n' + "Informe o n�mero do Personagem: ");

		try {
		    umaPersonagem = personagemAppService.recuperaUmaPersonagem(idPersonagem);
		} catch (PersonagemNaoEncontradaException e) {
		    System.out.println('\n' + e.getMessage());
		    break;
		}

		nome = Console.readLine('\n' + "Informe o nome do equipamento: ");
		tipo = Console.readLine("Informe o tipo de equipamento: ");
		elemento = Console.readLine("Informe o elemento do equipamento: ");

		umEquipamento = new Equipamento(nome, tipo,elemento ,umaPersonagem);

		try
		/* ==> */ {
		    equipamentoAppService.inclui(umEquipamento);

		    System.out.println('\n' + "Personagem adicionada com sucesso");
		} catch (PersonagemNaoEncontradaException e) {
		    System.out.println(e.getMessage());
		}

		break;
	    }

	    case 2: {
		int resposta = Console.readInt('\n' + "Digite o n�mero do equipamento que voc� deseja remover: ");

		try {
		    umEquipamento = equipamentoAppService.recuperaUmEquipamento(resposta);
		} catch (EquipamentoNaoEncontradoException e) {
		    System.out.println('\n' + e.getMessage());
		    break;
		}

		System.out.println('\n' + "N�mero = " + umEquipamento.getId() + "    nome = " +umEquipamento.getNome()+"    tipo="+ umEquipamento.getTipo()+"  elemento="+umEquipamento.getElemento());

		String resp = Console.readLine('\n' + "Confirma a remo��o do Equipamento? s/n");

		if (resp.equals("s")) {
		    try
		    /* ==> */ {
			equipamentoAppService.exclui(umEquipamento.getId());
			System.out.println('\n' + "equipamento removid com sucesso!");
		    } catch (EquipamentoNaoEncontradoException e) {
			System.out.println(e.getMessage());
		    }
		} else {
		    System.out.println('\n' + "Equipamento n�o removida.");
		}

		break;
	    }

	    case 3: {
	    	try {
	    long resposta = Console.readInt('\n' + "Informe o n�mero da Equipamento: ");
		umEquipamento = equipamentoAppService.recuperaUmEquipamentoEHabilidades(resposta);
		
		} catch (EquipamentoNaoEncontradoException e) {
		    System.out.println('\n' + e.getMessage());
		    break;
		}

	    	System.out.println('\n' + "N�mero = " + umEquipamento.getId() + "    nome = " +umEquipamento.getNome()+"    tipo="+ umEquipamento.getTipo()+"  elemento="+umEquipamento.getElemento());
	    	

		List<Habilidade> habilidades = umEquipamento.getHabilidades();

		for (Habilidade habilidade : habilidades) {
		    System.out.println('\n' + "      Habilidade n�mero = " + habilidade.getId() 
		                            + "  nome = " + habilidade.getNome()
		                            + "  efeito  = " + habilidade.getEfeito()
		    						+ "  cooldown = " + habilidade.getCooldown());
		}

		break;
	    }
	    
	    
	    case 4:{
	    	List<Equipamento> equipamentos = equipamentoAppService.recuperaEquipamentosEHabilidades();

			if (equipamentos.size() != 0) {
			    

			    for (Equipamento equipamento : equipamentos) {

			    	System.out.println('\n' + "N�mero = " + equipamento.getId() + "    nome = " +equipamento.getNome()+"    tipo="+ equipamento.getTipo()+"  elemento="+equipamento.getElemento());

				List<Habilidade> habilidades = equipamento.getHabilidades();

				for (Habilidade habilidade : habilidades) {
				    System.out.println('\n' + "      Habilidade n�mero = " + habilidade.getId() 
				                            + "  nome = " + habilidade.getNome()
				                            + "  efeito  = " + habilidade.getEfeito()
				    						+ "  cooldown = " + habilidade.getCooldown());
				}
			    }
			} else {
			    System.out.println('\n' + "N�o h� equipamentos cadastrados.");
			}

			break;
	    }

	    case 5: {
		continua = false;
		break;
	    }

	    default:
		System.out.println('\n' + "Op��o inv�lida!");
	    }
	}
    }
}