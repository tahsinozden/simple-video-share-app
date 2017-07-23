package ozden.app.common;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class FileService {
    public List<File> getAllFilesUnderDirectory(String path) {
        File folder = new File(path);
        return Arrays.asList(folder.listFiles());
    }

    public List<File> getAllFilesUnderDirectoryWithFileExtension(String path, String fileExtension) {
        List<File> allFiles = getAllFilesUnderDirectory(path);
        return allFiles.stream()
                .filter(file -> file.getName().toLowerCase().endsWith(fileExtension))
                .collect(Collectors.toList());
    }

    public Optional<File> getRandomFileWithExtension(String path, String fileExtension) {
        Random random = new Random();
        List<File> allSupportedFiles = getAllFilesUnderDirectoryWithFileExtension(path, fileExtension);
        return allSupportedFiles.isEmpty() ? Optional.empty() :
                Optional.of(allSupportedFiles.get(random.nextInt(allSupportedFiles.size())));
    }

    public InputStream getFileStream(String filePath) throws FileNotFoundException {
        return new FileInputStream(filePath);
    }
}
