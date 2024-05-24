package libs.Loader;

public interface FilePlugin {
    void load(String filePath);
    void save(String filepath);
    boolean supports(String fileExtension);
}
