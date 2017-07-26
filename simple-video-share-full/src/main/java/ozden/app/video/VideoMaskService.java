package ozden.app.video;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VideoMaskService {

    private final int VIDEO_VALIDITY_PERIOD = 1;
    private final int VIDEO_GARBAGE_CHECK_PERIOD_IN_MILLISECONDS = 10000;

    private static Map<String, VideoMaskBean> videoNameMapping = new ConcurrentHashMap<>();

    // for test purpose
    public void setVideoNameMapping(Map<String, VideoMaskBean> videoNameMapping) {
        this.videoNameMapping = videoNameMapping;
    }

    public String getMaskedVideoName(String realNameOfVideo) {
        LocalDateTime now = LocalDateTime.now();
        VideoMaskBean videoMaskBean = videoNameMapping.entrySet().stream()
                .filter(entry -> entry.getKey().equals(realNameOfVideo) && entry.getValue().getExpireTime().isAfter(now))
                .map(entry -> entry.getValue())
                .findFirst()
                .orElseGet(() -> {
                    VideoMaskBean videoMaskBean1 = new VideoMaskBean(realNameOfVideo, String.valueOf(realNameOfVideo.hashCode()), now.plusMinutes(VIDEO_VALIDITY_PERIOD));
                    videoNameMapping.put(realNameOfVideo, videoMaskBean1);
                    return videoMaskBean1;
                });

        return videoMaskBean.getMaskedName();
    }

    public Optional<String> getRealVideoName(String maskedVideoName) {
        VideoMaskBean videoMaskBean = null;
        for (VideoMaskBean videoMask : videoNameMapping.values()) {
            if (videoMask.getMaskedName().equals(maskedVideoName)) {
                videoMaskBean = videoMask;
            }
        }

        return videoMaskBean != null ? Optional.of(videoMaskBean.getRealName()) : Optional.empty();
    }

    @Scheduled(fixedDelay = VIDEO_GARBAGE_CHECK_PERIOD_IN_MILLISECONDS)
    private void videoGarbageCollection() {
        LocalDateTime now = LocalDateTime.now();
        videoNameMapping.entrySet().removeIf(entry -> entry.getValue().getExpireTime().isBefore(now));
    }
}
