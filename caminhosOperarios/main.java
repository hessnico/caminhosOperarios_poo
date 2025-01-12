package caminhosOperarios;

import java.util.*;

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

        Usuario usuarioAtual= auth.validaUsuario();
        if (usuarioAtual== null) {
            System.out.println("Erro na validação do usuário. Encerrando o programa.");
            return;
        }

        System.out.println("Bem-vindo, " + usuarioAtual.getUsername() + "! Tipo de usuário: " + usuarioAtual.getRole());

        projeto memorias = projeto.montaProjeto("Memorias");

        while (running) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Escolher projeto");
            if (usuarioAtual instanceof Curador || usuarioAtual instanceof Historiador) {
                System.out.println("2. Editar rota");
                System.out.println("3. Editar local");
            }
            if (usuarioAtual instanceof Historiador) {
                System.out.println("4. Criar rota");
                System.out.println("5. Criar local");
            }
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int escolhaMenu = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (escolhaMenu) {
                case 1: // Navegar em projeto
                    navegarProjeto(scanner, memorias, usuarioAtual);
                    break;

                case 2: // Editar rota (apenas Curador ou Historiador)
                    if (usuarioAtual instanceof Curador || usuarioAtual instanceof Historiador) {
                        editarRota(scanner, memorias, usuarioAtual);
                    } else {
                        System.out.println("Opção não disponível para seu tipo de usuário.");
                    }
                    break;

                case 3: // Editar local (apenas Curador ou Historiador)
                    if (usuarioAtual instanceof Curador || usuarioAtual instanceof Historiador) {
                        editarLocal(scanner, memorias, usuarioAtual);
                    } else {
                        System.out.println("Opção não disponível para seu tipo de usuário.");
                    }
                    break;

                case 4: // Criar rota (apenas Historiador)
                    if (usuarioAtual instanceof Historiador) {
                        criarRota(scanner, (Historiador) usuarioAtual, memorias);
                    } else {
                        System.out.println("Opção não disponível para seu tipo de usuário.");
                    }
                    break;

                case 5: // Criar local (apenas Historiador)
                    if (usuarioAtual instanceof Historiador) {
                        criarLocal(scanner, (Historiador) usuarioAtual, memorias);
                    } else {
                        System.out.println("Opção não disponível para seu tipo de usuário.");
                    }
                    break;

                case 0:
                    System.out.println("Encerrando o programa...");
                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    private static void navegarProjeto(Scanner scanner, projeto memorias, Usuario usuario) {
        System.out.println("\nProjeto Selecionado: " + memorias.nome);
        boolean navegandoRotas = true;

        while (navegandoRotas) {
            System.out.println("\n--- Rotas Disponíveis ---");
            memorias.getNomeRotas(); // Lista rotas
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma rota pelo ID: ");
            String rotaId = scanner.nextLine();

            if (rotaId.equals("0")) {
                navegandoRotas = false;
                break;
            }

            rota rotaSelecionada = memorias.getSpecificRoute(rotaId);
            if (rotaSelecionada != null) {
                usuario.visitarRota(rotaSelecionada); // Adiciona a rota à lista de visitadas
                memorias.navegaLocal(rotaSelecionada);
            } else {
                System.out.println("Rota não encontrada.");
            }
        }
    }

    private static void editarRota(Scanner scanner, projeto memorias, Usuario usuario) {
        System.out.println("\n--- Editar Rota ---");
        memorias.getNomeRotas();
        System.out.print("Escolha uma rota pelo ID para editar: ");
        String rotaId = scanner.nextLine();
        rota rotaSelecionada = memorias.getSpecificRoute(rotaId);

        if (rotaSelecionada != null) {
            System.out.print("Novo nome para a rota: ");
            String novoNome = scanner.nextLine();
            System.out.print("Nova descrição para a rota: ");
            String novaDescricao = scanner.nextLine();

            if (usuario instanceof Curador) {
                ((Curador) usuario).editarRota(rotaSelecionada, novoNome, novaDescricao);
            } else if (usuario instanceof Historiador) {
                ((Historiador) usuario).editarRota(rotaSelecionada, novoNome, novaDescricao);
            }
            System.out.println("Rota editada com sucesso!");
        } else {
            System.out.println("Rota não encontrada.");
        }
    }

    private static void editarLocal(Scanner scanner, projeto memorias, Usuario usuario) {
        System.out.println("\n--- Editar Local ---");
        memorias.getNomeRotas();
        System.out.print("Escolha uma rota pelo ID para selecionar um local: ");
        String rotaId = scanner.nextLine();
        rota rotaSelecionada = memorias.getSpecificRoute(rotaId);

        if (rotaSelecionada != null) {
            rotaSelecionada.consultaLocais();
            System.out.print("Escolha um local pelo nome para editar: ");
            String localNome = scanner.nextLine();
            local localSelecionado = rotaSelecionada.getLocal().stream()
                    .filter(l -> l.getNome().equalsIgnoreCase(localNome))
                    .findFirst()
                    .orElse(null);

            if (localSelecionado != null) {
                System.out.print("Novo nome para o local: ");
                String novoNome = scanner.nextLine();
                System.out.print("Nova descrição para o local: ");
                String novaDescricao = scanner.nextLine();
                System.out.print("Novas coordenadas para o local: (No estilo WKT... [POINT (-51.222 -30.029)])");
                String novasCoordenadas = scanner.nextLine();

                String novaUrlCoordenadas = utils.generateGoogleMapsLocalURL(novasCoordenadas);
                if (usuario instanceof Curador) {
                    ((Curador) usuario).editarLocal(localSelecionado, novoNome, novaDescricao, novaUrlCoordenadas);
                } else if (usuario instanceof Historiador) {
                    ((Historiador) usuario).editarLocal(localSelecionado, novoNome, novaDescricao, novaUrlCoordenadas);
                }
                System.out.println("Local editado com sucesso!");
            } else {
                System.out.println("Local não encontrado.");
            }
        } else {
            System.out.println("Rota não encontrada.");
        }
    }

    private static void criarRota(Scanner scanner, Historiador historiador, projeto memorias) {
        System.out.println("\n--- Criar Nova Rota ---");
        System.out.print("ID da nova rota: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha
        System.out.print("Nome da nova rota: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição da nova rota: ");
        String descricao = scanner.nextLine();

        historiador.criarRota(id, nome, descricao);
        // memorias.criarRota(id, nome, descricao);
        System.out.println("Atualizar rota atual não implementado!");
    }

    private static void criarLocal(Scanner scanner, Historiador historiador, projeto memorias) {
        System.out.println("\n--- Criar Novo Local ---");
        System.out.print("Nome do local: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição do local: ");
        String descricao = scanner.nextLine();
        System.out.print("Coordenadas do local: ");
        String coordenadas = scanner.nextLine();

        historiador.criarLocal(nome, coordenadas, descricao);
        System.out.println("Novo local criado com sucesso!");
    }
}