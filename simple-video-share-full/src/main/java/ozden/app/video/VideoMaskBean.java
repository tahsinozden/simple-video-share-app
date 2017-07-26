package ozden.app.video;

import java.time.LocalDateTime;

public class VideoMaskBean {
    private String realName;
    private String maskedName;
    private LocalDateTime expireTime;

    public VideoMaskBean(String realName, String maskedName, LocalDateTime expireTime) {
        this.realName = realName;
        this.maskedName = maskedName;
        this.expireTime = expireTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMaskedName() {
        return maskedName;
    }

    public void setMaskedName(String maskedName) {
        this.maskedName = maskedName;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VideoMaskBean that = (VideoMaskBean) o;

        if (realName != null ? !realName.equals(that.realName) : that.realName != null) return false;
        if (maskedName != null ? !maskedName.equals(that.maskedName) : that.maskedName != null) return false;
        return expireTime != null ? expireTime.equals(that.expireTime) : that.expireTime == null;
    }

    @Override
    public int hashCode() {
        int result = realName != null ? realName.hashCode() : 0;
        result = 31 * result + (maskedName != null ? maskedName.hashCode() : 0);
        result = 31 * result + (expireTime != null ? expireTime.hashCode() : 0);
        return result;
    }
}
