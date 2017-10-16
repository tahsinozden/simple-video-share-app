package ozden.app.video.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozden.app.video.persistance.UserToVideo;
import ozden.app.video.persistance.UserToVideoRepository;
import ozden.app.video.persistance.Video;
import ozden.app.video.persistance.VideoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserVideoService {
    @Autowired
    private UserToVideoRepository userToVideoRepository;

    @Autowired
    private VideoRepository videoRepository;

    public void saveUserVideo(String userName, List<Integer> videoIds) {
        List<Video> videos = videoRepository.findByVideoIdIn(videoIds);
        String ids = videos.stream()
                .map(video -> String.valueOf(video.getVideoId()))
                .collect(Collectors.joining(","));

        userToVideoRepository.save(new UserToVideo(userName, ids));
    }
}
