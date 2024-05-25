package libs.FileManager;
import java.io.*;

public interface FilePlugin {
    void load(File filePath);
    void save(String directory);
    boolean supports(String fileExtension);
}
