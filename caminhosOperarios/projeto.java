package caminhosOperarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private HashMap<String, rota> rotas;

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

    public projeto(String nome, String descricao, HashMap<String, rota> rotas) {
        this.nome = nome;
        this.descricao = descricao;
        this.rotas = rotas;
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
        for (String line : lines) {
            String[] partes = line.split(";");
            String localGoogleMapsUrl = utils.generateGoogleMapsLocalURL(partes[1]);

            local temp_local = new local(partes[2], localGoogleMapsUrl, partes[3]);

            String currentId = partes[0];
            int intCurrentId = Integer.parseInt(partes[0]);
            if (!hashmapRotas.containsKey(currentId)) {
                System.out.printf("\n    Não tenho o ID: %s e portanto estou adicionando no hashmap\n", partes[0]);

                System.out.print("    Adicionado rota atual no hashmap de rotas completas...");
                String currentRotaCompleta = utils.generateGoogleMapsRouteURL(partes[5]);
                rota currentRota = new rota(intCurrentId, partes[4], currentRotaCompleta);

                currentRota.adicionarLocal(temp_local);

                System.out.printf("\n    local de nome %s foi aficionado a rota de ID %s", temp_local.getNome(), currentId);
                hashmapRotas.put(currentId, currentRota);
            } else {

                rota currentRota = hashmapRotas.get(currentId);
                currentRota.adicionarLocal(temp_local);

                System.out.printf("\n    local de nome %s foi aficionado a rota de ID %s", temp_local.getNome(), currentId);
                hashmapRotas.put(currentId, currentRota);
            }
        }

        System.out.println("\n------------------------------------------\n");

        return new projeto("Memórias dos operários", constants.DESCRICAO_MEMORIAS, hashmapRotas);
    }
}
