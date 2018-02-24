package ozden.app.video.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ozden.app.auth.AuthBean;
import ozden.app.video.persistance.UserToVideo;

import java.util.List;

@RestController
@RequestMapping("/user/video")
public class UserVideoController {
    // TODO: implement real auth mechanism

    @Autowired
    private UserVideoService userVideoService;

    @PostMapping("save")
    public void saveUserVideo(@RequestBody RequestBean requestBean) {
        userVideoService.saveUserVideo(requestBean.getAuthBean(), requestBean.getVideoIds());
    }

    @GetMapping("{username}")
    public List<UserToVideo> getUserVideos(@PathVariable("username") String userName) {
        return userVideoService.findVideosByUserName(userName);
    }

    static final class RequestBean {
        AuthBean authBean;
        List<Integer> videoIds;

        public RequestBean() {
        }

        public RequestBean(AuthBean authBean, List<Integer> videoIds) {
            this.authBean = authBean;
            this.videoIds = videoIds;
        }

        public AuthBean getAuthBean() {
            return authBean;
        }

        public void setAuthBean(AuthBean authBean) {
            this.authBean = authBean;
        }

        public List<Integer> getVideoIds() {
            return videoIds;
        }

        public void setVideoIds(List<Integer> videoIds) {
            this.videoIds = videoIds;
        }
    }
}
