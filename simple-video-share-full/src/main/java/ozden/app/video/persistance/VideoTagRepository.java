package ozden.app.video.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoTagRepository extends JpaRepository<VideoTag, Integer> {
    List<VideoTag> findByTagId(Integer tagId);
    List<VideoTag> findByTagIdIn(List<Integer> tagIds);
    List<VideoTag> findByTagName(String tagName);
    List<VideoTag> findByTagNameIn(List<String> tagNames);
}
