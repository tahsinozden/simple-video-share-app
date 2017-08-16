package ozden.app.common;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import ozden.app.video.SupportedVideoFormat;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

public class FileServiceTest {

    private final String DIR_PATH = "tmpPath";

    @Spy
    private FileService fileService = new FileService();

    private List<File> allFiles;
    private List<File> supportedFiles;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        allFiles = Arrays.asList(
                new File("newVideo1.mp4"),
                new File("newVideo2.mp4"),
                new File("newVideo3.jpeg"),
                new File("newVideo4.mp4"),
                new File("newVideo5.avi"));

        supportedFiles = Arrays.asList(
                new File("newVideo1.mp4"),
                new File("newVideo2.mp4"),
                new File("newVideo4.mp4"));

        // doReturn should be used here because inside the method, there is a real file creation
        doReturn(allFiles).when(fileService).getAllFilesUnderDirectory(DIR_PATH);
    }

    @Test
    public void shouldGetAllFilesUnderDirectoryWithFileExtension() throws Exception {
        List<File> actualSupportedFiles = fileService.getAllFilesUnderDirectoryWithFileExtension(DIR_PATH, SupportedVideoFormat.MP4.getName());
        assertEquals(3, actualSupportedFiles.size());
        assertEquals(supportedFiles, actualSupportedFiles);
    }

    @Test
    public void shouldGetRandomFileWithExtension() throws Exception {
        Optional<File> actualRandomFileOptional = fileService.getRandomFileWithExtension(DIR_PATH, SupportedVideoFormat.MP4.getName());
        assertTrue(actualRandomFileOptional.isPresent());
        assertTrue(actualRandomFileOptional.get().getName().endsWith(SupportedVideoFormat.MP4.getName()));
    }

}