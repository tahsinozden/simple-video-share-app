package ozden.app.video.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserToVideoRepository extends JpaRepository<UserToVideo, String> {
    List<UserToVideo> findByUserName(String userName);
}
