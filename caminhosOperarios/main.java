package caminhosOperarios;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

/*
Informações do banco de dados
    0: rota ID
    1: wkt com o valor do ponto específico
    2: nome do local
    3: descrição
    4: nome da rota
    5: rota completa

TODO
    Switch-case para as opções do usuário
    Logs da execução do montaProjeto

Fluxograma do uso:
        1. Escolhe o projeto
        2. Monta o projeto
        3. O usuário pega informações. Adiciona visitas. Etc...
        4. Usuário sai do projeto
        5. Usuário entra em outro projeto
            5.1.O usuário pega informações. Adiciona visitas. Etc...
            5.2 O usuário sai do projeto
        6. Usuário termina execução do programa

Observações:
    Na query do google maps temos que passar lng + lat
*/

public class main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        HashMap<String, String> dadosUsuario = auth.validaUsuario();
        if (dadosUsuario == null || dadosUsuario.isEmpty()) {
            System.out.println("Erro na validação do usuário. Encerrando o programa.");
            return;
        }

        projeto memorias = projeto.montaProjeto("Memorias");
        memorias.getNomeRotas();
        memorias.getRotasCompleta();
        memorias.getInformacaoLocais();

        while (running) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Escolher projeto");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            int choiceMenu = scanner.nextInt();
            scanner.nextLine();

            switch (choiceMenu) {
                case 1:
                    System.out.println("\nProjeto Selecionado: " + memorias.getNomeRotas());
                    boolean choiceRota = true;

                    while (choiceRota) {
                        System.out.println("\n--- Rotas Disponíveis ---");
                        memorias.getNomeRotas();
                        System.out.println("0. Voltar ao menu principal");
                        System.out.print("Escolha uma rota pelo ID: ");
                        String rotaId = scanner.nextLine();

                        if (rotaId.equals("0")) {
                            choiceRota = false;
                            break;
                        }

                        rota rotaSelecionada = memorias.getNomeRotas();
                        if (rotaSelecionada != null) {
                            System.out.println("\nRota Selecionada: " + rotaSelecionada.getNome());
                            boolean navegandoLocais = true;

                            while (navegandoLocais) {
                                System.out.println("\n--- Locais na Rota ---");
                                rotaSelecionada.consultaLocais();
                                System.out.println("0. Voltar à seleção de rotas");
                                System.out.print("Escolha um local pelo nome: ");
                                String localNome = scanner.nextLine();

                                if (localNome.equals("0")) {
                                    navegandoLocais = false;
                                    break;
                                }

                                ArrayList<local> locais = rotaSelecionada.getLocal();
                                local localSelecionado = null;
                                for (local l : locais) {
                                    if (l.getNome().equalsIgnoreCase(localNome)) {
                                        localSelecionado = l;
                                        break;
                                    }
                                }

                                if (localSelecionado != null) {
                                    System.out.println("\n--- Informações do Local ---");
                                    System.out.println("Nome: " + localSelecionado.getNome());
                                    System.out.println("Descrição: " + localSelecionado.getDescricao());
                                    System.out.println("Coordenadas: " + localSelecionado.getUrlCoordenadas());
                                } else {
                                    System.out.println("Local não encontrado. Tente novamente.");
                                }
                            }
                        } else {
                            System.out.println("Rota não encontrada. Tente novamente.");
                        }
                    }
                    break;

                case 2:
                    System.out.println("Encerrando o programa...");
                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }
}