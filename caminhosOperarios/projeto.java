package caminhosOperarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a project that organizes routes and locations.
 *
 * Class: projeto
 *
 * Fields:
 *   - id: static int
 *       A static counter to uniquely identify each created instance of the `projeto` class.
 *       Auto-incremented with each new instance.
 *
 *   - nome: String
 *       The name of the project.
 *
 *   - descricao: String
 *       A textual description of the project.
 *
 *   - rotas: HashMap<String, rota>
 *       A mapping between route names (keys) and their corresponding `rota` objects (values).
 *
 * Constructors:
 *   - projeto(String nome, String descricao, HashMap<String, rota> rotas)
 *       Initializes a new `projeto` object with the given name, description, and routes.
 *
 *       Input Parameters:
 *           nome: String
 *               The name of the project.
 *           descricao: String
 *               The description of the project.
 *           rotas: HashMap<String, rota>
 *               A map of route names and their corresponding `rota` objects.
 *
 * Methods:
 *   - getInformacaoLocais(): void
 *       Prints detailed information about all locations across all routes in the project.
 *
 *       Input Parameters:
 *           None.
 *
 *       Returns:
 *           None.
 *
 *   - getInformacaoLocal(String nomeRota): void
 *       Prints detailed information about locations for a specific route.
 *
 *       Input Parameters:
 *           nomeRota: String
 *               The name of the route whose location details are to be printed.
 *
 *       Returns:
 *           None.
 *
 *   - getNomeRotas(): void
 *       Prints the names and IDs of all routes in the project.
 *
 *       Input Parameters:
 *           None.
 *
 *       Returns:
 *           None.
 *
 *
 *   - getDescricao(): void
 *       Prints the description of the project.
 *
 *       Input Parameters:
 *           None.
 *
 *       Returns:
 *           None.
 *
 *   - getRotasCompleta(): void
 *       Prints the complete route URLs for all routes in the project.
 *
 *       Input Parameters:
 *           None.
 *
 *       Returns:
 *           None.
 *
 *
 *   - static projeto montaProjeto(String projeto): projeto
 *       Creates a `projeto` object by reading data from a CSV file, parsing it into routes and locations,
 *       and organizing them into the project's structure.
 *
 *       Input Parameters:
 *           projeto: String
 *               The name of the project, used to locate the corresponding CSV file.
 *
 *       Returns:
 *           projeto:
 *               A fully initialized `projeto` object.
 *
 * Notes:
 *   - The `montaProjeto` method assumes that data is stored in a CSV file with a specific format.
 *     Each row represents a location and its associated route information.
 *   - The class is designed to encapsulate project details, making it easy to retrieve and display
 *     information about routes and their locations.
 */


public class projeto {

    private static int id = 0;
    public String nome;
    private String descricao;

    // esse hashmap vai ser responsável por mapear o nome da rota com a rota em si;
    private static HashMap<String, rota> rotas;

    private static HashMap<Integer, local> locais;

    public rota getSpecificRoute(String id) {
        return rotas.get(id);
    }

    public void navegaLocal(rota rotaSelecionada) {

        System.out.println("\nRota Selecionada: " + rotaSelecionada.getNome());
        boolean navegandoLocais = true;

        Scanner scanner = new Scanner(System.in);
        while (navegandoLocais) {

            System.out.println("\n--- Locais na Rota ---");
            rotaSelecionada.consultaLocais();

            System.out.println("\n-1. Voltar à seleção de rotas");
            System.out.println("0: Para imprimir a rota completa");
            System.out.print("Escolha um local pelo nome: ");
            int localId = scanner.nextInt();

            if (localId == -1) {
                navegandoLocais = false;
                break;
            }

            if (localId == 0) {
                System.out.println(rotaSelecionada.getRotaCompleta());
                scanner.nextLine();
                continue;
            }

            boolean validaIDLocal = rotaSelecionada.validaID(localId);
            if (!validaIDLocal) {
                System.out.println("Local não encontrado. Tente novamente.");
                continue;
            }

            local localSelecionado = locais.get(localId);

            if (localSelecionado != null) {
                System.out.println("\n--- Informações do Local ---");
                System.out.println("Nome: " + localSelecionado.getNome());
                System.out.println("Descrição: " + localSelecionado.getDescricao());
                System.out.println("Coordenadas: " + localSelecionado.getUrlCoordenadas());
                scanner.nextLine();
            } else {
                System.out.println("Local não encontrado. Tente novamente.");
            }
        }
    }

    public void getInformacaoLocais() {
        System.out.println("Informação todos os locais para todas as rotas");
        for (String key: rotas.keySet()) {
            rota rota = rotas.get(key);
            ArrayList<local> locais = rota.getLocal();
            System.out.println("    rota: " + rota.getNome());
            for (caminhosOperarios.local local : locais) {
                System.out.println("        Nome: " + local.getNome());
                System.out.println("        Descrição: " + local.getDescricao());
                System.out.println("        ------------ ");
            }
        }
        System.out.println();
    }

    public void getInformacaoLocal(String nomeRota) {
        rota rota = rotas.get(nomeRota);
        System.out.println("Informação de locais para a rota: " + rota.getNome());
        ArrayList<local> locais = rota.getLocal();
        for (caminhosOperarios.local local : locais) {
            System.out.println("    Nome: " + local.getNome());
            System.out.println("    Descrição: " + local.getDescricao());
            System.out.println("    ------------ ");
        }
        System.out.println();
    }

    public rota getNomeRotas() {
        System.out.println("Nome das rotas");
        for (String key: rotas.keySet()) {
            rota rota = rotas.get(key);
            System.out.println("    ID: " + rota.getId() + "- " + rota.getNome());
        }

        System.out.println();
        return null;
    }

    public void getDescricao() {
        System.out.printf("\nDescriçao do projeto: %s\n", descricao);
    }

    public projeto(String nome, String descricao, HashMap<String, rota> rotas, HashMap<Integer, local> locais) {
        this.nome = nome;
        this.descricao = descricao;
        this.rotas = rotas;
        this.locais = locais;
        id++;
    }

    public void getRotasCompleta() {
        System.out.println("Rotas completas");
        for (String key: rotas.keySet()) {
            rota rota = rotas.get(key);
            System.out.println("    " + rota.getNome() + ": " + rota.getRotaCompleta());
        }
        System.out.println();
    }

    public static projeto montaProjeto(String projeto) {

        List<String> lines = commaSeparatedValuesHandler.readCsv(constants.getValueFromKey(projeto), constants.ENCONDING);
        lines.removeFirst();

        HashMap<String, rota> hashmapRotas = new HashMap<String, rota>();
        HashMap<Integer, local> hashMapLocais = new HashMap<Integer, local>();

        for (String line : lines) {
            String[] partes = line.split(";");
            String localGoogleMapsUrl = utils.generateGoogleMapsLocalURL(partes[1]);

            local temp_local = new local(partes[2], localGoogleMapsUrl, partes[3]);
            hashMapLocais.put(local.getId(), temp_local);

            String currentId = partes[0];
            int intCurrentId = Integer.parseInt(partes[0]);
            if (!hashmapRotas.containsKey(currentId)) {
                // System.out.printf("\n    Não tenho o ID: %s e portanto estou adicionando no hashmap\n", partes[0]);
                // System.out.print("    Adicionado rota atual no hashmap de rotas completas...");

                String currentRotaCompleta = utils.generateGoogleMapsRouteURL(partes[5]);
                rota currentRota = new rota(intCurrentId, partes[4], currentRotaCompleta);

                currentRota.adicionarLocal(temp_local);

                // System.out.printf("\n    local de nome %s foi aficionado a rota de ID %s", temp_local.getNome(), currentId);

                hashmapRotas.put(currentId, currentRota);
            } else {

                rota currentRota = hashmapRotas.get(currentId);
                currentRota.adicionarLocal(temp_local);

                // System.out.printf("\n    local de nome %s foi aficionado a rota de ID %s", temp_local.getNome(), currentId);
                hashmapRotas.put(currentId, currentRota);
            }
        }

        // System.out.println("\n------------------------------------------\n");

        return new projeto("Memórias dos operários", constants.DESCRICAO_MEMORIAS, hashmapRotas, hashMapLocais);
    }

    public void navegarProjeto(Scanner scanner, Usuario usuario) {
        System.out.println("\nProjeto Selecionado: " + nome);
        boolean navegandoRotas = true;

        while (navegandoRotas) {
            System.out.println("\n--- Rotas Disponíveis ---");
            getNomeRotas(); // Lista rotas
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma rota pelo ID: ");
            String rotaId = scanner.nextLine();

            if (rotaId.equals("0")) {
                navegandoRotas = false;
                break;
            }

            rota rotaSelecionada = getSpecificRoute(rotaId);
            if (rotaSelecionada != null) {
                usuario.visitarRota(rotaSelecionada); // Adiciona a rota à lista de visitadas
                navegaLocal(rotaSelecionada);
            } else {
                System.out.println("Rota não encontrada.");
            }
        }
    }

    public void editarRota(Scanner scanner, projeto memorias, Usuario usuario) {
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

    public void editarLocal(Scanner scanner, projeto memorias, Usuario usuario) {
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

    public void criarRota(Scanner scanner, Historiador historiador) {
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

    public void criarLocal(Scanner scanner, Historiador historiador) {
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
