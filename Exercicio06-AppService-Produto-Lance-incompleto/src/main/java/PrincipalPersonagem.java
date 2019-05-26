import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import corejava.Console;
import excecao.JogadorNaoEncontradoException;
import excecao.PersonagemNaoEncontradaException;
import modelo.Equipamento;
import modelo.Jogador;
import modelo.Personagem;
import service.JogadorAppService;
import service.PersonagemAppService;

public class PrincipalPersonagem {
	public static void main(String[] args) {
		String nome;
		String sexo;
		String classe;

		Jogador umJogador;
		Personagem umaPersonagem;

		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");
		JogadorAppService jogadorAppService = (JogadorAppService)fabrica.getBean ("jogadorAppService");
		PersonagemAppService personagemAppService = (PersonagemAppService)fabrica.getBean ("personagemAppService");
		

		boolean continua = true;
		while (continua) {
			System.out.println('\n' + "O que você deseja fazer?");
			/* ==> */ System.out.println('\n' + "1. Cadastrar uma personagem de um jogador");
			System.out.println("\n2. Alterar um personagem");
			/* ==> */ System.out.println("3. Remover um personagem");
			System.out.println("4. Recuperar uma personagem e todos os seus equipamentos");
			System.out.println("5. Recuperar Todas as personagens e seus equipamentos");
			System.out.println("6. Sair");

			int opcao = Console.readInt('\n' + "Digite um número entre 1 e 6:");

			switch (opcao) {
			case 1: {
				long idJogador = Console.readInt('\n' + "Informe o número do jogador: ");

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
				int resposta = Console.readInt('\n' + "Digite o número do personagem que você deseja alterar: ");

				try {
					umaPersonagem = personagemAppService.recuperaUmaPersonagem(resposta);
					
				} catch (PersonagemNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out
						.println('\n' + "Número = " + umaPersonagem.getId() + "    nome = " + umaPersonagem.getNome()+"  sexo = "+ umaPersonagem.getSexo()+"   classe= "+umaPersonagem.getClasse());

				System.out.println('\n' + "O que você deseja alterar?");
				System.out.println('\n' + "1. Nome");
				System.out.println("\n2. Classe");

				int opcaoAlteracao = Console.readInt('\n' + "Digite um número de 1 a 2:");

				switch (opcaoAlteracao) {
				case 1:
					String nomeChar = Console.readLine("Digite o novo Nome: ");
					umaPersonagem.setNome(nomeChar);

					try {
						personagemAppService.altera(umaPersonagem);

						System.out.println('\n' + "Alteração de personagem efetuada com sucesso!");
					} catch (PersonagemNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;

				case 2:
					String classeChar = Console.readLine("Digite a nova classe: ");
					umaPersonagem.setClasse(classeChar);

					try {
						personagemAppService.altera(umaPersonagem);

						System.out.println('\n' + "Alteração de descrição efetuada " + "com sucesso!");
					} catch (PersonagemNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;

				default:
					System.out.println('\n' + "Opção inválida!");
				}

				break;
			}


			case 3: {
				int resposta = Console.readInt('\n' + "Digite o número da personagem que você deseja remover: ");

				try {
					umaPersonagem = personagemAppService.recuperaUmaPersonagem(resposta);
				} catch (PersonagemNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Número = " + umaPersonagem.getId() + "    Nome = " + umaPersonagem.getNome()
						+ "    Sexo = " + umaPersonagem.getSexo() + "    classe=" + umaPersonagem.getClasse());

				String resp = Console.readLine('\n' + "Confirma a remoção da personagem? S/N");

				if (resp.equals("s")) {
					try
					/* ==> */ {
						personagemAppService.exclui(umaPersonagem.getId());
						System.out.println('\n' + "Personagem removida com sucesso!");
					} catch (PersonagemNaoEncontradaException e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println('\n' + "Personagem não removida.");
				}

				break;
			}

			case 4: {
				try {
					long resposta = Console.readInt('\n' + "Informe o número da Personagem: ");
					umaPersonagem = personagemAppService.recuperaUmaPersonagemEEquipamentos(resposta);

				} catch (PersonagemNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Número = " + umaPersonagem.getId() + "    Nome = " + umaPersonagem.getNome()
						+ "    Sexo = " + umaPersonagem.getSexo() + "    classe=" + umaPersonagem.getClasse());

				List<Equipamento> equipamentos = umaPersonagem.getEquipamentos();

				for (Equipamento equipamento : equipamentos) {
					System.out.println('\n' + "      Equipamento número = " + equipamento.getId() + "  nome = "
							+ equipamento.getNome() + "  tipo  = " + equipamento.getTipo() + "  elemento = "
							+ equipamento.getElemento());
				}

				break;
			}

			case 5: {
				List<Personagem> personagems = personagemAppService.recuperaPersonagensEEquipamentos();

				if (personagems.size() != 0) {
					System.out.println("");

					for (Personagem personagem : personagems) {

						System.out.println('\n' + "Número = " + personagem.getId() + "    Sexo = "
								+ personagem.getSexo() + "    classe=" + personagem.getClasse());

						List<Equipamento> equipamentos = personagem.getEquipamentos();

						for (Equipamento equipamento : equipamentos) {
							System.out.println('\n' + "      Equipamento número = " + equipamento.getId() + "  nome = "
									+ equipamento.getNome() + "  tipo  = " + equipamento.getTipo() + "  elemento = "
									+ equipamento.getElemento());
						}
					}
				} else {
					System.out.println('\n' + "Não há Personagens cadastradas.");
				}

				break;
			}

			case 6: {
				continua = false;
				break;
			}

			default:
				System.out.println('\n' + "Opção inválida!");
			}
		}
	}
}
