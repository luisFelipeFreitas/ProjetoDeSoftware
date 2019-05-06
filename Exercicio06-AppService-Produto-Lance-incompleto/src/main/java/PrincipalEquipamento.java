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
			System.out.println('\n' + "O que você deseja fazer?");
			/* ==> */ System.out.println('\n' + "1. Cadastrar um equipamento de uma personagem");
			System.out.println("2. Alterar um equipamento");
			/* ==> */ System.out.println("3. Remover um equipamento");
			
			System.out.println("4. Recuperar um equipamento e todas as suas habilidades");
			System.out.println("5. Recuperar todos os equipamentos e suas habilidades");
			System.out.println("6. Sair");

			int opcao = Console.readInt('\n' + "Digite um número entre 1 e 6:");

			switch (opcao) {
			case 1: {
				long idPersonagem = Console.readInt('\n' + "Informe o número do Personagem: ");

				try {
					umaPersonagem = personagemAppService.recuperaUmaPersonagem(idPersonagem);
				} catch (PersonagemNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				nome = Console.readLine('\n' + "Informe o nome do equipamento: ");
				tipo = Console.readLine("Informe o tipo de equipamento: ");
				elemento = Console.readLine("Informe o elemento do equipamento: ");

				umEquipamento = new Equipamento(nome, tipo, elemento, umaPersonagem);

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
				int resposta = Console.readInt('\n' + "Digite o número do equipamento que você deseja alterar: ");

				try {
					umEquipamento = equipamentoAppService.recuperaUmEquipamento(resposta);
					
				} catch (EquipamentoNaoEncontradoException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out
						.println('\n' + "Número = " + umEquipamento.getId() + "    nome = " + umEquipamento.getNome()+"  Elemento = "+ umEquipamento.getElemento()+"   Tipo= "+umEquipamento.getTipo());

				System.out.println('\n' + "O que você deseja alterar?");
				System.out.println('\n' + "1. Nome");
				System.out.println("\n2. Tipo");
				System.out.println("\n3. Elemento");

				int opcaoAlteracao = Console.readInt('\n' + "Digite um número de 1 a 3:");

				switch (opcaoAlteracao) {
				case 1:
					String nomeChar = Console.readLine("Digite o novo Nome: ");
					umEquipamento.setNome(nomeChar);

					try {
						equipamentoAppService.altera(umEquipamento);

						System.out.println('\n' + "Alteração de equipamento efetuada com sucesso!");
					} catch (EquipamentoNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;

				case 2:
					String tipoEquip = Console.readLine("Digite o novo tipo: ");
					umEquipamento.setTipo(tipoEquip);

					try {
						equipamentoAppService.altera(umEquipamento);

						System.out.println('\n' + "Alteração de descrição efetuada " + "com sucesso!");
					} catch (EquipamentoNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;
				case 3:
					String elementoEquip = Console.readLine("Digite o novo elemento: ");
					umEquipamento.setElemento(elementoEquip);

					try {
						equipamentoAppService.altera(umEquipamento);

						System.out.println('\n' + "Alteração de descrição efetuada " + "com sucesso!");
					} catch (EquipamentoNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;

				default:
					System.out.println('\n' + "Opção inválida!");
				}

				break;
			}


			case 3: {
				int resposta = Console.readInt('\n' + "Digite o número do equipamento que você deseja remover: ");

				try {
					umEquipamento = equipamentoAppService.recuperaUmEquipamento(resposta);
				} catch (EquipamentoNaoEncontradoException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Número = " + umEquipamento.getId() + "    nome = " + umEquipamento.getNome()
						+ "    tipo=" + umEquipamento.getTipo() + "  elemento=" + umEquipamento.getElemento());

				String resp = Console.readLine('\n' + "Confirma a remoção do Equipamento? s/n");

				if (resp.equals("s")) {
					try
					/* ==> */ {
						equipamentoAppService.exclui(umEquipamento.getId());
						System.out.println('\n' + "equipamento removid com sucesso!");
					} catch (EquipamentoNaoEncontradoException e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println('\n' + "Equipamento não removida.");
				}

				break;
			}

			case 4: {
				try {
					long resposta = Console.readInt('\n' + "Informe o número da Equipamento: ");
					umEquipamento = equipamentoAppService.recuperaUmEquipamentoEHabilidades(resposta);

				} catch (EquipamentoNaoEncontradoException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Número = " + umEquipamento.getId() + "    nome = " + umEquipamento.getNome()
						+ "    tipo=" + umEquipamento.getTipo() + "  elemento=" + umEquipamento.getElemento());

				List<Habilidade> habilidades = umEquipamento.getHabilidades();

				for (Habilidade habilidade : habilidades) {
					System.out.println('\n' + "      Habilidade número = " + habilidade.getId() + "  nome = "
							+ habilidade.getNome() + "  efeito  = " + habilidade.getEfeito() + "  cooldown = "
							+ habilidade.getCooldown());
				}

				break;
			}

			case 5: {
				List<Equipamento> equipamentos = equipamentoAppService.recuperaEquipamentosEHabilidades();

				if (equipamentos.size() != 0) {

					for (Equipamento equipamento : equipamentos) {

						System.out.println('\n' + "Número = " + equipamento.getId() + "    nome = "
								+ equipamento.getNome() + "    tipo=" + equipamento.getTipo() + "  elemento="
								+ equipamento.getElemento());

						List<Habilidade> habilidades = equipamento.getHabilidades();

						for (Habilidade habilidade : habilidades) {
							System.out.println('\n' + "      Habilidade número = " + habilidade.getId() + "  nome = "
									+ habilidade.getNome() + "  efeito  = " + habilidade.getEfeito() + "  cooldown = "
									+ habilidade.getCooldown());
						}
					}
				} else {
					System.out.println('\n' + "Não há equipamentos cadastrados.");
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
