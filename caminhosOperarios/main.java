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
                    memorias.navegarProjeto(scanner, usuarioAtual);
                    break;

                case 2: // Editar rota (apenas Curador ou Historiador)
                    if (usuarioAtual instanceof Curador || usuarioAtual instanceof Historiador) {
                        memorias.editarRota(scanner, memorias, usuarioAtual);
                    } else {
                        System.out.println("Opção não disponível para seu tipo de usuário.");
                    }
                    break;

                case 3: // Editar local (apenas Curador ou Historiador)
                    if (usuarioAtual instanceof Curador || usuarioAtual instanceof Historiador) {
                        memorias.editarLocal(scanner, memorias, usuarioAtual);
                    } else {
                        System.out.println("Opção não disponível para seu tipo de usuário.");
                    }
                    break;

                case 4: // Criar rota (apenas Historiador)
                    if (usuarioAtual instanceof Historiador) {
                        memorias.criarRota(scanner, (Historiador) usuarioAtual);
                    } else {
                        System.out.println("Opção não disponível para seu tipo de usuário.");
                    }
                    break;

                case 5: // Criar local (apenas Historiador)
                    if (usuarioAtual instanceof Historiador) {
                        memorias.criarLocal(scanner, (Historiador) usuarioAtual);
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
}