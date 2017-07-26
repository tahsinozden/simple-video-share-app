package ozden.app.video;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class VideoMaskServiceTest {

    private VideoMaskService videoMaskService = new VideoMaskService();
    private Map<String, VideoMaskBean> maskedVideos = new ConcurrentHashMap<>();

    @Before
    public void setUp() {

    }

    @Test
    public void shouldGetMaskedVideoNameWhenVideoExistsInsideMap() throws Exception {
        String videoName = "video1";
        String videoHashCode = String.valueOf(videoName.hashCode());
        VideoMaskBean videoMaskBean1 = new VideoMaskBean(videoName, videoHashCode, LocalDateTime.now());
        maskedVideos.put(videoName, videoMaskBean1);

        String maskedVideoName = videoMaskService.getMaskedVideoName(videoName);

        assertNotNull(maskedVideoName);
        assertEquals(maskedVideoName, videoHashCode);
    }

    @Test
    public void shouldGetMaskedVideoNameWhenVideoNotExistInsideMap() throws Exception {
        String videoName = "video1";
        String videoHashCode = String.valueOf(videoName.hashCode());

        String maskedVideoName = videoMaskService.getMaskedVideoName(videoName);

        assertNotNull(maskedVideoName);
        assertEquals(maskedVideoName, videoHashCode);
    }

    @Test
    public void shouldGetRealVideoNameIfVideoWasAddedToMapBefore() throws Exception {
        String videoName = "video1";
        String videoHashCode = String.valueOf(videoName.hashCode());
        VideoMaskBean videoMaskBean1 = new VideoMaskBean(videoName, videoHashCode, LocalDateTime.now());
        maskedVideos.put(videoName, videoMaskBean1);

        String videoName2 = "video2";
        String videoHashCode2 = String.valueOf(videoName2.hashCode());
        VideoMaskBean videoMaskBean2 = new VideoMaskBean(videoName2, videoHashCode2, LocalDateTime.now().plusMinutes(2));
        maskedVideos.put(videoName2, videoMaskBean2);

        videoMaskService.setVideoNameMapping(maskedVideos);
        Optional<String> realVideoNameOptional = videoMaskService.getRealVideoName(videoHashCode2);

        assertTrue(realVideoNameOptional.isPresent());
        assertNotNull(realVideoNameOptional.get());
        assertEquals(realVideoNameOptional.get(), videoName2);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenRealVideoNameIsRequestedIfVideoIsExpired() throws Exception {
        String videoName = "video1";
        String videoHashCode = String.valueOf(videoName.hashCode());
        VideoMaskBean videoMaskBean1 = new VideoMaskBean(videoName, videoHashCode, LocalDateTime.now().minusMinutes(3));
        maskedVideos.put(videoName, videoMaskBean1);

        videoMaskService.setVideoNameMapping(maskedVideos);
        Optional<String> realVideoNameOptional = videoMaskService.getRealVideoName(videoName);

        assertFalse(realVideoNameOptional.isPresent());
    }

    @Test
    public void shouldReturnEmptyOptionalWhenRealVideoNameIsRequestedIfVideoNotExistInMap() throws Exception {
        String videoName = "video1";
        String videoHashCode = String.valueOf(videoName.hashCode());
        VideoMaskBean videoMaskBean1 = new VideoMaskBean(videoName, videoHashCode, LocalDateTime.now().minusMinutes(3));
        maskedVideos.put(videoName, videoMaskBean1);

        videoMaskService.setVideoNameMapping(maskedVideos);
        Optional<String> realVideoNameOptional = videoMaskService.getRealVideoName("nonExistingVideoNameHere");

        assertFalse(realVideoNameOptional.isPresent());
    }
}