package caminhosOperarios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading data from CSV files
 *
 * Class: commaSeparatedValuesHandler
 *
 * Methods:
 *   - readCsv(String filePath, Charset encoding): List<String>
 *       Reads all lines from a CSV file located at the specified file path.
 *
 *       Input Parameters:
 *           filePath: String
 *               The path to the CSV file to be read.
 *           encoding: Charset
 *               The character encoding of the CSV file (e.g., StandardCharsets.UTF_8).
 *
 *       Returns:
 *           List<String>:
 *               A list of strings, where each string represents a line from the file.
 *               Returns an empty list if the file cannot be read.
 *
 *   - createBufferedReader(String filePath, Charset encoding): BufferedReader
 *       Creates a BufferedReader object for the specified file and encoding.
 *
 *       Input Parameters:
 *           filePath: String
 *               The path to the CSV file.
 *           encoding: Charset
 *               The character encoding of the file.
 *
 *       Returns:
 *           BufferedReader:
 *               A BufferedReader object for reading the file.
 *               Returns null if the file cannot be opened.
 *
 *   - readLines(BufferedReader reader): List<String>
 *       Reads all lines from a BufferedReader into a list.
 *
 *       Input Parameters:
 *           reader: BufferedReader
 *               The BufferedReader object to read lines from.
 *
 *       Returns:
 *           List<String>:
 *               A list of strings containing all lines read from the reader.
 *               Returns an empty list if an error occurs during reading.
 *
 *   - closeReader(BufferedReader reader): void
 *       Safely closes a BufferedReader object.
 *
 *       Input Parameters:
 *           reader: BufferedReader
 *               The BufferedReader object to be closed.
 *
 *       Returns:
 *           None.
 *           Prints an error message to the console if closing the reader fails.
 *
 * Notes:
 *   - The class uses exception handling to ensure robustness:
 *       - If the file cannot be opened, a message is printed, and an empty list is returned.
 *       - If an error occurs during reading, the process is halted gracefully.
 *       - The BufferedReader is always closed after reading, even in case of errors.
 *   - Useful for reading CSV files where each line represents a record.
 *
 */

public class commaSeparatedValuesHandler {

    public static List<String> readCsv(String filePath, Charset encoding) {
        BufferedReader reader = createBufferedReader(filePath, encoding);
        if (reader == null) {
            return new ArrayList<>();
        }
        return readLines(reader);
    }

    private static BufferedReader createBufferedReader(String filePath, Charset encoding) {
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
        } catch (Exception e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
            return null;
        }
    }

    private static List<String> readLines(BufferedReader reader) {
        List<String> lines = new ArrayList<>();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler as linhas do arquivo: " + e.getMessage());
        } finally {
            closeReader(reader);
        }
        return lines;
    }

    private static void closeReader(BufferedReader reader) {
        try {
            reader.close();
        } catch (Exception e) {
            System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
        }
    }

}
