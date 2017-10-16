package ozden.app.video.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findByVideoId(Integer videoId);
    List<Video> findByVideoName(String videoName);
    List<Video> findByVideoFilePathLike(String pathLike);

    List<Video> findByVideoIdIn(List<Integer> videoIds);
}
