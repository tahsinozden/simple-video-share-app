package ozden.app.video.persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserToVideo")
public class UserToVideo {
    @Id
    @Column(name = "userName")
    private String userName;

    @Column(name = "videoId")
    private String videoIds;

    public UserToVideo() {
    }

    public UserToVideo(String userName, String videoIds) {
        this.userName = userName;
        this.videoIds = videoIds;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVideoIds() {
        return videoIds;
    }

    public void setVideoIds(String videoIds) {
        this.videoIds = videoIds;
    }
}
