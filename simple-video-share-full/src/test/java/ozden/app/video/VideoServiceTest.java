package ozden.app.video;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ozden.app.common.FileService;

import java.io.File;
import java.io.FileInputStream;
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
    }

    @Test
    public void shouldGetRandomVideoWithValidPath() throws Exception {
        Optional<File> randomFile = Optional.of(new File("newFile.mp4"));
        when(fileService.getRandomFileWithExtension(DIR_PATH, SupportedVideoFormat.MP4.getName())).thenReturn(randomFile);

        Optional<String> randomVideo = videoService.getRandomVideoName(DIR_PATH);
        assertTrue(randomVideo.isPresent());
        assertTrue(randomVideo.get().endsWith(SupportedVideoFormat.MP4.getName()));
    }

    @Test
    public void shouldNotGetRandomVideoWithInvalidPath() throws Exception {
        Optional<File> randomFile = Optional.empty();
        when(fileService.getRandomFileWithExtension("invalidPath", SupportedVideoFormat.MP4.getName())).thenReturn(randomFile);

        Optional<String> randomVideo = videoService.getRandomVideoName("invalidPath");
        assertFalse(randomVideo.isPresent());
    }

    @Test
    public void shouldGetVideoStreamWithValidVideoPath() throws Exception {
        when(fileService.getFileStream(DIR_PATH)).thenReturn(fileInputStream);
        assertNotNull(videoService.getVideoStream(DIR_PATH));
        verify(fileService).getFileStream(DIR_PATH);
    }

}