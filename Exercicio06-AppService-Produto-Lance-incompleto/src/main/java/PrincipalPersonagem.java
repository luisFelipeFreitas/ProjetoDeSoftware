import java.util.List;

import corejava.Console;
import excecao.PersonagemNaoEncontradaException;
import excecao.JogadorNaoEncontradoException;
import modelo.Personagem;
import modelo.Jogador;
import modelo.Equipamento;
import servico.PersonagemAppService;
import servico.JogadorAppService;
import util.Util;

public class PrincipalPersonagem {
	public static void main(String[] args) {
		String nome;
		String sexo;
		String classe;

		Jogador umJogador;
		Personagem umaPersonagem;

		JogadorAppService jogadorAppService = new JogadorAppService();
		PersonagemAppService personagemAppService = new PersonagemAppService();

		boolean continua = true;
		while (continua) {
			System.out.println('\n' + "O que voc� deseja fazer?");
			/* ==> */ System.out.println('\n' + "1. Cadastrar uma personagem de um jogador");
			/* ==> */ System.out.println("2. Remover um personagem");
			System.out.println("3. Recuperar uma personagem e todos os seus equipamentos");
			System.out.println("4. Recuperar Todas as personagens e seus equipamentos");
			System.out.println("5. Sair");

			int opcao = Console.readInt('\n' + "Digite um n�mero entre 1 e 5:");

			switch (opcao) {
			case 1: {
				long idJogador = Console.readInt('\n' + "Informe o n�mero do jogador: ");

				try {
					umJogador = jogadorAppService.recuperaUmJogador(idJogador);
				} catch (JogadorNaoEncontradoException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				nome = Console.readLine('\n' + "Informe o nome da Personagem: ");
				sexo = Console.readLine("Informe o sexo da personagem: ");
				classe = Console.readLine("Informe a classe: ");

				umaPersonagem = new Personagem(nome, sexo, classe, umJogador);

				try
				/* ==> */ {
					personagemAppService.inclui(umaPersonagem);

					System.out.println('\n' + "Personagem adicionada com sucesso");
				} catch (JogadorNaoEncontradoException e) {
					System.out.println(e.getMessage());
				}

				break;
			}

			case 2: {
				int resposta = Console.readInt('\n' + "Digite o n�mero da personagem que voc� deseja remover: ");

				try {
					umaPersonagem = personagemAppService.recuperaUmaPersonagem(resposta);
				} catch (PersonagemNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "N�mero = " + umaPersonagem.getId() + "    Nome = " + umaPersonagem.getNome()
						+ "    Sexo = " + umaPersonagem.getSexo() + "    classe=" + umaPersonagem.getClasse());

				String resp = Console.readLine('\n' + "Confirma a remo��o da personagem? S/N");

				if (resp.equals("s")) {
					try
					/* ==> */ {
						personagemAppService.exclui(umaPersonagem.getId());
						System.out.println('\n' + "Personagem removida com sucesso!");
					} catch (PersonagemNaoEncontradaException e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println('\n' + "Personagem n�o removida.");
				}

				break;
			}

			case 3: {
				try {
					long resposta = Console.readInt('\n' + "Informe o n�mero da Personagem: ");
					umaPersonagem = personagemAppService.recuperaUmaPersonagemEEquipamentos(resposta);

				} catch (PersonagemNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "N�mero = " + umaPersonagem.getId() + "    Nome = " + umaPersonagem.getNome()
						+ "    Sexo = " + umaPersonagem.getSexo() + "    classe=" + umaPersonagem.getClasse());

				List<Equipamento> equipamentos = umaPersonagem.getEquipamentos();

				for (Equipamento equipamento : equipamentos) {
					System.out.println('\n' + "      Equipamento n�mero = " + equipamento.getId() + "  nome = "
							+ equipamento.getNome() + "  tipo  = " + equipamento.getTipo() + "  elemento = "
							+ equipamento.getElemento());
				}

				break;
			}

			case 4: {
				List<Personagem> personagems = personagemAppService.recuperaPersonagensEEquipamentos();

				if (personagems.size() != 0) {
					System.out.println("");

					for (Personagem personagem : personagems) {

						System.out.println('\n' + "N�mero = " + personagem.getId() + "    Sexo = "
								+ personagem.getSexo() + "    classe=" + personagem.getClasse());

						List<Equipamento> equipamentos = personagem.getEquipamentos();

						for (Equipamento equipamento : equipamentos) {
							System.out.println('\n' + "      Equipamento n�mero = " + equipamento.getId() + "  nome = "
									+ equipamento.getNome() + "  tipo  = " + equipamento.getTipo() + "  elemento = "
									+ equipamento.getElemento());
						}
					}
				} else {
					System.out.println('\n' + "N�o h� Personagens cadastradas.");
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