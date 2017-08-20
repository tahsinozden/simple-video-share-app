package ozden.app.video.persistance;

import javax.persistence.*;

@Entity
@Table(name="VideoTags")
public class VideoTag {

    @Id
    @GeneratedValue
    @Column(name="tagId")
    private Integer tagId;

    @Column(name="tagName")
    private String tagName;

    public VideoTag() {
    }

    public VideoTag(String tagName) {
        this.tagName = tagName;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
