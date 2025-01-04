package caminhosOperarios;

import java.util.ArrayList;

/**
 * A base class representing a user in the system.
 *
 * Attributes:
 *    username: String
 *        The username of the user.
 *    password: String
 *        The password of the user.
 *    role: String
 *        The role of the user (e.g., "usuário", "historiador", "curador").
 *    id: int
 *        The unique identifier for the user.
 *    rotasVisitadas: ArrayList<rota>
 *        A list of routes visited by the user.
 *    locaisVisitados: ArrayList<local>
 *        A list of locations visited by the user.
 *
 * Methods:
 *    visitarRota: ArrayList<local>
 *        Adds a route to the list of visited routes and returns the locations of the route.
 *    verInfoRota: String
 *        Returns information about a selected route, including its name, description, and locations.
 *    verInfoLocal: String
 *        Returns detailed information about a selected location and its associated route.
 *    visitarLocal: String
 *        Adds a location to the list of visited locations if not already visited and returns detailed information about it.
 */

class Usuario {
    private final String username;
    private final String password;
    private final String role; // Escopo: usuário, historiador, curador
    private static int id;
    private ArrayList<rota> rotasVisitadas; // Lista de rotas visitadas pelo usuário
    private ArrayList<local> locaisVisitados; // Lista de locais visitados pelo usuário

    public Usuario(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        id++;
        this.rotasVisitadas = new ArrayList<>();
        this.locaisVisitados = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    // Método: visitarRota
    public ArrayList<local> visitarRota(rota rotaSelecionada) {
        if (!rotasVisitadas.contains(rotaSelecionada)) {
            rotasVisitadas.add(rotaSelecionada);
            rotaSelecionada.registerVisita();
            locaisVisitados.addAll(rotaSelecionada.getLocal());
        }
        return rotaSelecionada.getLocal();
    }

    // Método: verInfoRota
    public String verInfoRota(rota rotaSelecionada) {
        return String.format("Rota: %s\nDescrição: %s\nLocais:\n%s",
                rotaSelecionada.getNome(),
                rotaSelecionada.getRotaCompleta(),
                rotaSelecionada.getLocal().stream()
                        .map(local -> "  - " + local.getNome())
                        .reduce("", (a, b) -> a + "\n" + b).trim());
    }

    // Método: verInfoLocal
    public String verInfoLocal(local localSelecionado, ArrayList<rota> rotas) {
        for (rota r : rotas) {
            if (r.getLocal().contains(localSelecionado)) {
                return String.format("Local: %s\nDescrição: %s\nCoordenadas: %s\nParte da rota: %s",
                        localSelecionado.getNome(),
                        localSelecionado.getDescricao(),
                        localSelecionado.getUrlCoordenadas(),
                        r.getNome());
            }
        }
        return "Local não encontrado em nenhuma rota.";
    }

    // Método: visitarLocal
    public String visitarLocal(local localSelecionado, ArrayList<rota> rotas) {
        for (rota r : rotas) {
            if (r.getLocal().contains(localSelecionado)) {
                if (!locaisVisitados.contains(localSelecionado)) {
                    locaisVisitados.add(localSelecionado);
                }
                return String.format("Local: %s\nDescrição: %s\nCoordenadas: %s\nParte da rota: %s",
                        localSelecionado.getNome(),
                        localSelecionado.getDescricao(),
                        localSelecionado.getUrlCoordenadas(),
                        r.getNome());
            }
        }
        return "Local não encontrado em nenhuma rota.";
    }
}

/**
 * A class representing a historian user in the system.
 * Inherits from Usuario and has additional capabilities for creating and editing routes, locations, and projects.
 *
 * Additional Attributes:
 *    rotasEditadas: ArrayList<rota>
 *        A list of routes edited by the historian.
 *    rotasCriadas: ArrayList<rota>
 *        A list of routes created by the historian.
 *    locaisEditados: ArrayList<local>
 *        A list of locations edited by the historian.
 *    locaisCriados: ArrayList<local>
 *        A list of locations created by the historian.
 *    projetosCriados: ArrayList<String>
 *        A list of projects created by the historian.
 *    projetosEditados: ArrayList<String>
 *        A list of projects edited by the historian.
 *
 * Additional Methods:
 *    criarRota: void
 *        Creates a new route and adds it to the list of created routes.
 *    editarRota: void
 *        Edits an existing route and adds it to the list of edited routes.
 *    criarLocal: void
 *        Creates a new location and adds it to the list of created locations.
 *    editarLocal: void
 *        Edits an existing location and adds it to the list of edited locations.
 *    criarProjeto: void
 *        Creates a new project and adds it to the list of created projects.
 *    editarProjeto: void
 *        Edits an existing project and updates the list of created projects.
 */

class Historiador extends Usuario {

    private ArrayList<rota> rotasEditadas; // Rotas editadas pelo historiador
    private ArrayList<rota> rotasCriadas; // Rotas criadas pelo historiador
    private ArrayList<local> locaisEditados; // Locais editados pelo historiador
    private ArrayList<local> locaisCriados; // Locais criados pelo historiador
    private ArrayList<String> projetosCriados; // Projetos criados pelo historiador
    private ArrayList<String> projetosEditados; // Projetos editados pelo historiador

    public Historiador(String username, String password, String role) {
        super(username, password, role);
        this.rotasEditadas = new ArrayList<>();
        this.rotasCriadas = new ArrayList<>();
        this.locaisEditados = new ArrayList<>();
        this.locaisCriados = new ArrayList<>();
        this.projetosCriados = new ArrayList<>();
        this.projetosEditados = new ArrayList<>();
    }

    public ArrayList<rota> getRotasEditadas() {
        return rotasEditadas;
    }

    public ArrayList<rota> getRotasCriadas() {
        return rotasCriadas;
    }

    public ArrayList<local> getLocaisEditados() {
        return locaisEditados;
    }

    public ArrayList<local> getLocaisCriados() {
        return locaisCriados;
    }

    public ArrayList<String> getProjetosCriados() {
        return projetosCriados;
    }

    public ArrayList<String> getProjetosEditados() {
        return projetosEditados;
    }

    // Métodos para criar e editar rotas
    public void criarRota(int id, String nome, String rotaCompleta) {
        rota novaRota = new rota(id, nome, rotaCompleta);
        rotasCriadas.add(novaRota);
    }

    public void editarRota(rota rotaSelecionada, String novoNome, String novaDescricao) {
        if (!rotasEditadas.contains(rotaSelecionada)) {
            rotasEditadas.add(rotaSelecionada);
        }
        rotaSelecionada.verDescricao();
        rotaSelecionada = new rota(rotaSelecionada.getId(), novoNome, novaDescricao);
    }

    // Métodos para criar e editar locais
    public void criarLocal(String nome, String urlCoordenadas, String descricao) {
        local novoLocal = new local(nome, urlCoordenadas, descricao);
        locaisCriados.add(novoLocal);
    }

    public void editarLocal(local localSelecionado, String novoNome, String novaDescricao, String novasCoordenadas) {
        if (!locaisEditados.contains(localSelecionado)) {
            locaisEditados.add(localSelecionado);
        }
        localSelecionado = new local(novoNome, novasCoordenadas, novaDescricao);
    }

    // Métodos para criar e editar projetos
    public void criarProjeto(String nomeProjeto) {
        projetosCriados.add(nomeProjeto);
    }

    public void editarProjeto(String nomeProjetoAntigo, String nomeProjetoNovo) {
        if (!projetosEditados.contains(nomeProjetoAntigo)) {
            projetosEditados.add(nomeProjetoAntigo);
        }
        projetosCriados.remove(nomeProjetoAntigo);
        projetosCriados.add(nomeProjetoNovo);
    }
}

/**
 * A class representing a curator user in the system.
 * Inherits from Usuario and has additional capabilities for editing routes and locations.
 *
 * Additional Attributes:
 *    rotasEditadas: ArrayList<rota>
 *        A list of routes edited by the curator.
 *    locaisEditados: ArrayList<local>
 *        A list of locations edited by the curator.
 *
 * Methods:
 *    editarRota: void
 *        Edits an existing route and adds it to the list of edited routes.
 *    editarLocal: void
 *        Edits an existing location and adds it to the list of edited locations.
 */

class Curador extends Usuario {

    private ArrayList<rota> rotasEditadas; // Rotas editadas pelo curador
    private ArrayList<local> locaisEditados; // Locais editados pelo curador

    public Curador(String username, String password, String role) {
        super(username, password, role);

        this.rotasEditadas = new ArrayList<>();
        this.locaisEditados = new ArrayList<>();

    }

    public ArrayList<rota> getRotasEditadas() {
        return rotasEditadas;
    }

    public ArrayList<local> getLocaisEditados() {
        return locaisEditados;
    }

    // Método para editar rotas
    public void editarRota(rota rotaSelecionada, String novoNome, String novaDescricao) {
        if (!rotasEditadas.contains(rotaSelecionada)) {
            rotasEditadas.add(rotaSelecionada);
        }
        rotaSelecionada = new rota(rotaSelecionada.getId(), novoNome, novaDescricao);
    }

    // Método para editar locais
    public void editarLocal(local localSelecionado, String novoNome, String novaDescricao, String novasCoordenadas) {
        if (!locaisEditados.contains(localSelecionado)) {
            locaisEditados.add(localSelecionado);
        }
        localSelecionado = new local(novoNome, novasCoordenadas, novaDescricao);
    }

}