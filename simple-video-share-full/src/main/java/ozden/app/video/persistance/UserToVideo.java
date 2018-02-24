package ozden.app.video.persistance;

import javax.persistence.*;

@Entity
@Table(name = "UserToVideo")
public class UserToVideo {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "videoId")
    private String videoIds;

    public UserToVideo(String userName, String videoIds) {
        this.userName = userName;
        this.videoIds = videoIds;
    }

    public UserToVideo() {
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
