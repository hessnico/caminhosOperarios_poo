package caminhosOperarios;

import java.util.*;

/**
 * Manages user authentication and database initialization
 *
 * Class: auth
 *
 * Methods:
 *   - montaDatabaseUsuarios(): boolean
 *       Initializes the user database by reading a CSV file and populating a HashMap.
 *
 *       Input Parameters:
 *           None
 *
 *       Returns:
 *           boolean:
 *               True if the database is successfully created, False otherwise.
 *
 *   - autenticaUsuario(String username, String password): Usuario
 *       Authenticates a user by validating the provided username and password.
 *
 *       Input Parameters:
 *           username: String
 *               The username provided by the user.
 *           password: String
 *               The password provided by the user.
 *
 *       Returns:
 *           Usuario:
 *               The authenticated user object if credentials are valid.
 *               Null if authentication fails.
 *
 *   - validaUsuario(): HashMap<String, String>
 *       Orchestrates user authentication by interacting with the user through the CLI.
 *
 *       Input Parameters:
 *           None
 *
 *       Returns:
 *           HashMap<String, String>:
 *               A HashMap containing the authenticated user's username and role (e.g., "user1" -> "admin").
 *               Null if authentication fails.
 *
 * Class Fields:
 *   - dummyDatabase: Map<String, Usuario>
 *       A HashMap that simulates a database for storing user credentials and roles.
 *
 *       Key: String
 *           The username.
 *       Value: Usuario
 *           The corresponding user object containing username, password, and role information.
 *
 * Notes:
 *   - Requires a CSV file with user data located at constants.FILEPATH_USUARIOS.
 *   - The CSV should be formatted with a semicolon (;) delimiter and encoded per constants.ENCONDING.
 *   - The class assumes a CLI-based interaction for username and password input.
 *   - Handles errors gracefully when database initialization fails or authentication is invalid.
 *
 */

public class auth {

    private static boolean montaDatabaseUsuarios() {

        List<String> lines = commaSeparatedValuesHandler.readCsv(constants.FILEPATH_USUARIOS, constants.ENCONDING);
        lines.removeFirst();

        for (String line : lines) {
            String[] partes = line.split(";");

            System.out.println(Arrays.toString(partes));
            try {
                dummyDatabase.put(partes[0], new Usuario(partes[0], partes[1], partes[2]));
            } catch (Exception e) {
                System.out.printf("Não foi possível montar o banco de dados: %s", e);
                return false;
            }
        }
        System.out.println("Banco de dados dos usuários montado com sucesso.");
        return true;

    }

    private static Map<String, Usuario> dummyDatabase = new HashMap<>();

    private static Usuario autenticaUsuario(String username, String password) {
        Usuario user = dummyDatabase.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public static HashMap<String, String> validaUsuario() {

        if (!montaDatabaseUsuarios()) {
            System.out.println("Erro em valida usuario. Não foi possível de montar o banco de dados");
            return null;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Sistema de Autenticação CLI!");
        System.out.print("Usuário: ");
        String username = scanner.nextLine();

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        // Autenticação
        Usuario usuarioAutenticado = auth.autenticaUsuario(username, password);
        if (usuarioAutenticado != null) {
            System.out.println("Autenticação bem-sucedida!");
            System.out.println("Bem-vindo, " + usuarioAutenticado.getUsername() + "!");
            System.out.println("Seu escopo é: " + usuarioAutenticado.getRole());
        } else {
            System.out.println("Falha na autenticação. Verifique suas credenciais.");
            return null;
        }

        HashMap<String, String> dadosUsuario = new HashMap<String, String>();
        dadosUsuario.put(usuarioAutenticado.getUsername(), usuarioAutenticado.getRole());

        scanner.close();
        return dadosUsuario;
    }
}
