package caminhosOperarios;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Stores constant values and utility methods for the application
 *
 * Class: constants
 *
 * Fields:
 *   - DESCRICAO_MEMORIAS: String
 *       A description of the "Caminhos Operários" project, detailing its purpose and history.
 *
 *   - FILEPATH_USUARIOS: String
 *       The file path to the CSV containing user data.
 *
 *   - FILEPATH_MEMORIAS: String
 *       The file path to the CSV containing project memory data.
 *
 *   - ENCONDING: Charset
 *       The default character encoding for reading files.
 *
 *   - mapProjetoToFilePath: HashMap<String, String>
 *       A static map linking project names to their corresponding file paths.
 *
 * Methods:
 *   - getValueFromKey(String key): String
 *       Retrieves the file path associated with a given project name.
 *
 *       Input Parameters:
 *           key: String
 *               The name of the project for which the file path is needed.
 *
 *       Returns:
 *           String:
 *               The file path associated with the given key.
 *               Returns null if the key does not exist in the map.
 *
 * Notes:
 *   - The class is designed to centralize static configuration details, ensuring consistent access throughout the application.
 *   - The `mapProjetoToFilePath` map is initialized in a static block, allowing for easy extension by adding more project-to-path mappings.
 *   - The constants are primarily used in file handling and project descriptions.
 *
 */

public class constants {
    public static final String DESCRICAO_MEMORIAS = "O Caminhos Operários em Porto Alegre é um projeto organizado pelo historiador Frederico Duarte Bartz, que busca resgatar a memória da classe trabalhadora e de suas organizações no período formativo do movimento operário local, através de aulas e caminhadas temáticas. Desde 2019, ele vem sendo oferecido em forma de cursos de extensão pela Biblioteca da Faculdade de Arquitetura da UFRGS, local onde seu organizador atua como técnico em assuntos educacionais.";
    public static final String FILEPATH_USUARIOS = "./dados/usuarios.csv";
    public static final String FILEPATH_MEMORIAS = "./dados/dados.csv";
    public static final Charset ENCONDING = StandardCharsets.UTF_8;

    private static HashMap<String, String> mapProjetoToFilePath = new HashMap<>();

    static {
        mapProjetoToFilePath.put("Memorias", FILEPATH_MEMORIAS);
    }

    public static String getValueFromKey(String key) {
        return mapProjetoToFilePath.get(key);
    }
}
