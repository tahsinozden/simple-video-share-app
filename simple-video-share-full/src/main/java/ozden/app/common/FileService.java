package ozden.app.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class FileService {
    @Value("${multipart.location}")
    String savePath;

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

    public File saveFile(MultipartFile file) throws IOException {
        String fileName = savePath + "file_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
//        File fileToBeSaved = new File(fileName);
        StringBuilder fileFullPathName = new StringBuilder()
                .append(savePath)
                .append("file_")
                .append(System.currentTimeMillis())
                .append("_")
                .append(file.getOriginalFilename());

        File fileToBeSaved = Paths.get(fileFullPathName.toString())
                .normalize()
                .toFile();

        file.transferTo(fileToBeSaved);

        return fileToBeSaved;
    }
}
