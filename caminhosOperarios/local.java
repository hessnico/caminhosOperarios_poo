package caminhosOperarios;

/**
 * Represents a specific location with a name, coordinates, and description
 *
 * Class: local
 *
 * Fields:
 *   - id: static int
 *       A static counter to uniquely identify each created instance of the `local` class.
 *       Auto-incremented with each new instance.
 *
 *   - nome: String
 *       The name of the location.
 *
 *   - urlCoordenadas: String
 *       A URL or reference string containing the coordinates of the location.
 *
 *   - descricao: String
 *       A textual description of the location.
 *
 * Constructors:
 *   - local(String nome, String urlCoordenadas, String descricao)
 *       Initializes a new `local` object with the given name, coordinates, and description.
 *       Automatically increments the `id` field.
 *
 *       Input Parameters:
 *           nome: String
 *               The name of the location.
 *           urlCoordenadas: String
 *               The URL or coordinates of the location.
 *           descricao: String
 *               The description of the location.
 *
 *       Example:
 *           local myLocation = new local("Praça da Matriz", "https://maps.example.com/matriz", "Um marco histórico na cidade.");
 *
 * Methods:
 *   - verDescricao(): void
 *       Prints the description of the location to the console.
 *
 *       Input Parameters:
 *           None.
 *
 *       Returns:
 *           None.
 *
 *       Example Output:
 *           "Descrição do local: Um marco histórico na cidade."
 *
 *   - getDescricao(): String
 *       Retrieves the description of the location.
 *
 *       Input Parameters:
 *           None.
 *
 *       Returns:
 *           String:
 *               The description of the location.
 *
 *   - getNome(): String
 *       Retrieves the name of the location.
 *
 *       Input Parameters:
 *           None.
 *
 *       Returns:
 *           String:
 *               The name of the location.
 *
 *   - getUrlCoordenadas(): String
 *       Retrieves the URL or coordinates of the location.
 *
 *       Input Parameters:
 *           None.
 *
 *       Returns:
 *           String:
 *               The coordinates or URL of the location.
 *
 *   - descreveLocal(): void
 *       Prints a detailed description of the location, including its name, coordinates, and description.
 *
 *       Input Parameters:
 *           None.
 *
 *       Returns:
 *           None.
 *
 *       Example Output:
 *           "Descrição do local
 *               Nome: Praça da Matriz
 *               Coordenadas: https://maps.example.com/matriz
 *               Descrição: Um marco histórico na cidade."
 *
 * Notes:
 *   - The class is designed to encapsulate information about a location, making it easy to retrieve and display details.
 *   - The static `id` field ensures each instance can be uniquely identified if needed.
 */

public class local {

    private static int id = 0;
    public String nome;
    private String urlCoordenadas;
    private String descricao;

    public void verDescricao() {
        System.out.printf("\nDescriçao do local: %s\n", descricao);
    }

    public local(String nome, String urlCoordenadas, String descricao) {
        this.nome = nome;
        this.urlCoordenadas = urlCoordenadas;
        this.descricao = descricao;
        id++;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getUrlCoordenadas() {
        return urlCoordenadas;
    }

    public void descreveLocal() {
        System.out.println("Descriçao do local");
        System.out.printf("    Nome: %s\n", getNome());
        System.out.printf("    Coordenadas: %s\n", getUrlCoordenadas());
        System.out.printf("    Descriçao: %s\n", getDescricao());

    }
}
