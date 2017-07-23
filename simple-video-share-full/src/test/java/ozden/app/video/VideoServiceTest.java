package ozden.app.video;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ozden.app.common.FileService;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VideoServiceTest {

    private final String DIR_PATH = "tmpPath";

    @Mock
    private FileInputStream fileInputStream;

    @Mock
    private FileService fileService;

    @InjectMocks
    private VideoService videoService = new VideoService();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        List<File> allFiles = Arrays.asList(
                new File("newVideo1.mp4"),
                new File("newVideo2.mp4"),
                new File("newVideo3.jpeg"),
                new File("newVideo4.mp4"),
                new File("newVideo5.avi"));

        List<File> supportedFiles = Arrays.asList(
                new File("newVideo1.mp4"),
                new File("newVideo2.mp4"),
                new File("newVideo4.mp4"));



//        when(fileService.getAllFilesUnderDirectory(DIR_PATH)).thenReturn(allFiles);
//        when(fileService.getAllFilesUnderDirectoryWithFileExtension(DIR_PATH, SupportedVideoFormat.MP4.getName())).thenReturn(supportedFiles);


    }

    @Test
    public void shouldGetRandomVideoWithValidPath() throws Exception {
        Optional<File> randomFile = Optional.of(new File("newFile.mp4"));
        when(fileService.getRandomFileWithExtension(DIR_PATH, SupportedVideoFormat.MP4.getName())).thenReturn(randomFile);

        String randomVideo = videoService.getRandomVideo(DIR_PATH);
        assertNotNull(randomVideo);
        assertTrue(randomVideo.endsWith(SupportedVideoFormat.MP4.getName()));
    }

    @Test
    public void shouldNotGetRandomVideoWithInvalidPath() throws Exception {
        Optional<File> randomFile = Optional.empty();
        when(fileService.getRandomFileWithExtension("invalidPath", SupportedVideoFormat.MP4.getName())).thenReturn(randomFile);

        String randomVideo = videoService.getRandomVideo("invalidPath");
        assertEquals("", randomVideo);
    }

    @Test
    public void shouldGetVideoStreamWithValidVideoPath() throws Exception {
        when(fileService.getFileStream(DIR_PATH)).thenReturn(fileInputStream);
        assertNotNull(videoService.getVideoStream(DIR_PATH));
        verify(fileService).getFileStream(DIR_PATH);
    }

}