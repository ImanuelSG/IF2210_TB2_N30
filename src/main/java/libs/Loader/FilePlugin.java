package libs.Loader;

public interface FilePlugin {
    void load(String filePath);
    boolean supports(String fileExtension);
}
