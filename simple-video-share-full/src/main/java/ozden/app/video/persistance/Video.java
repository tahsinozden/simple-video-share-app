package ozden.app.video.persistance;

import javax.persistence.*;

@Entity
@Table(name="Videos")
public class Video {
    @Id
    @GeneratedValue
    @Column(name="videoId")
    private Integer videoId;

    @Column(name="videoName")
    private String videoName;

    @Column(name="videoDescription")
    private String videoDescription;

//    @OneToMany
    @Column(name="videoTagIds")
    private String videoTagIds;

    @Column(name="videoFilePath")
    private String videoFilePath;

    @Column(name="addedDate")
    private Long addedDate;

    public Video() {
    }

    public Video(String videoName, String videoDescription, String videoTagIds, String videoFilePath, Long addedDate) {
        this.videoName = videoName;
        this.videoDescription = videoDescription;
        this.videoTagIds = videoTagIds;
        this.videoFilePath = videoFilePath;
        this.addedDate = addedDate;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getVideoTagIds() {
        return videoTagIds;
    }

    public void setVideoTagIds(String videoTagIds) {
        this.videoTagIds = videoTagIds;
    }

    public String getVideoFilePath() {
        return videoFilePath;
    }

    public void setVideoFilePath(String videoFilePath) {
        this.videoFilePath = videoFilePath;
    }

    public Long getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Long addedDate) {
        this.addedDate = addedDate;
    }
}
