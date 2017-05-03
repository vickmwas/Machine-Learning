import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static java.nio.file.Paths.get;

/**
 * Created by marko on 4/20/17.
 */
public class FileUtility {

    public static String readTextFile(String filename) throws IOException {

        String content = new String(Files.readAllBytes(get(filename)));

        return content;
    }

    public static List<String> readTextFileByLines(String filename) throws IOException {

        List<String> lines = Files.readAllLines(get(filename));

        return lines;
    }

    public static void writeToTextFile(String filename, String content) throws IOException {

        Files.write(Paths.get(filename), content.getBytes(), StandardOpenOption.CREATE);

    }

}
