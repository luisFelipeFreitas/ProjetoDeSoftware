import java.util.List;

import corejava.Console;
import excecao.EquipamentoNaoEncontradoException;
import excecao.HabilidadeNaoEncontradaException;
import modelo.Habilidade;
import modelo.Equipamento;
import servico.HabilidadeAppService;
import servico.EquipamentoAppService;

public class PrincipalHabilidade {
	public static void main(String[] args) {
		String nome;
		String efeito;
		double cooldown;
		Equipamento umEquipamento;
		Habilidade umaHabilidade;

		EquipamentoAppService equipamentoAppService = new EquipamentoAppService();
		HabilidadeAppService habilidadeAppService = new HabilidadeAppService();

		boolean continua = true;
		while (continua) {
			System.out.println('\n' + "O que voc� deseja fazer?");
			/* ==> */ System.out.println('\n' + "1. Cadastrar uma habilidade de um equipamento");
			System.out.println("2. Alterar uma habilidade");
			/* ==> */ System.out.println("3. Remover uma habilidade");
			System.out.println("4. Recuperar todas as habilidades");
			System.out.println("5. Sair");

			int opcao = Console.readInt('\n' + "Digite um n�mero entre 1 e 5:");

			switch (opcao) {
			case 1: {
				long idEquipamento = Console.readInt('\n' + "Informe o n�mero do equipamento: ");

				try {
					umEquipamento = equipamentoAppService.recuperaUmEquipamento(idEquipamento);
				} catch (EquipamentoNaoEncontradoException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				cooldown = Console.readDouble('\n' + "Informe o cooldown da habilidade: ");
				nome = Console.readLine("Informe o nome da habilidade: ");
				efeito = Console.readLine("Informe o efeito da habilidade ");

				umaHabilidade = new Habilidade(cooldown, nome, efeito, umEquipamento);
				

				try
				/* ==> */ {
					habilidadeAppService.inclui(umaHabilidade);

					System.out.println('\n' + "Habilidade adicionada com sucesso");
				} catch (EquipamentoNaoEncontradoException e) {
					System.out.println(e.getMessage());
				}

				break;
			}
			case 2: {
				int resposta = Console.readInt('\n' + "Digite o n�mero da habilidade que voc� deseja alterar: ");

				try {
					umaHabilidade = habilidadeAppService.recuperaUmaHabilidade(resposta);

				} catch (HabilidadeNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "N�mero = " + umaHabilidade.getId() + "    nome = " + umaHabilidade.getNome()
						+ "  Cooldown = " + umaHabilidade.getCooldown() + "   Efeito= " + umaHabilidade.getEfeito());

				System.out.println('\n' + "O que voc� deseja alterar?");
				System.out.println('\n' + "1. Nome");
				System.out.println("\n2. Cooldown");
				System.out.println("\n3. Efeito");

				int opcaoAlteracao = Console.readInt('\n' + "Digite um n�mero de 1 a 3:");

				switch (opcaoAlteracao) {
				case 1:
					String nomeHab = Console.readLine("Digite o novo Nome: ");
					umaHabilidade.setNome(nomeHab);

					try {
						habilidadeAppService.altera(umaHabilidade);

						System.out.println('\n' + "Altera��o de equipamento efetuada com sucesso!");
					} catch (HabilidadeNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;

				case 2:
					double cooldownHab = Console.readDouble("Digite o novo Cooldown: ");
					umaHabilidade.setCooldown(cooldownHab);

					try {
						habilidadeAppService.altera(umaHabilidade);

						System.out.println('\n' + "Altera��o de equipamento efetuada com sucesso!");
					} catch (HabilidadeNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;
				case 3:
					String efeitoHab = Console.readLine("Digite o novo Efeito: ");
					umaHabilidade.setEfeito(efeitoHab);

					try {
						habilidadeAppService.altera(umaHabilidade);

						System.out.println('\n' + "Altera��o de equipamento efetuada com sucesso!");
					} catch (HabilidadeNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;
				default:
					System.out.println('\n' + "Op��o inv�lida!");
				}

				break;
			}

			case 3: {
				int resposta = Console.readInt('\n' + "Digite o n�mero da habilidade que voc� deseja remover: ");

				try {
					umaHabilidade = habilidadeAppService.recuperaUmaHabilidade(resposta);
				} catch (HabilidadeNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println(
						'\n' + "N�mero = " + umaHabilidade.getId() + "    Cooldown = " + umaHabilidade.getCooldown()
								+ "    Nome: = " + umaHabilidade.getNome() + "  Efeito: " + umaHabilidade.getEfeito());

				String resp = Console.readLine('\n' + "Confirma a remo��o da habilidade?");

				if (resp.equals("s")) {
					try
					/* ==> */ {
						habilidadeAppService.exclui(umaHabilidade.getId());
						System.out.println('\n' + "Habilidade removida com sucesso!");
					} catch (HabilidadeNaoEncontradaException e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println('\n' + "Habilidade n�o removido.");
				}

				break;
			}

			case 4: {
				List<Habilidade> arrayHabilidades = habilidadeAppService.recuperaHabilidades();

				if (arrayHabilidades.size() == 0) {
					System.out.println('\n' + "Nao h� habilidades cadastrados.");
					break;
				}

				System.out.println("");
				for (Habilidade habilidade : arrayHabilidades) {
					System.out.println(
							'\n' + "N�mero = " + habilidade.getId() + "    Cooldown = " + habilidade.getCooldown()
									+ "    Nome: = " + habilidade.getNome() + "  Efeito: " + habilidade.getEfeito());
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
