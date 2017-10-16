package ozden.app.video.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/video")
public class UserVideoController {
    // TODO: implement real auth mechanism

    @Autowired
    private UserVideoService userVideoService;

    @PostMapping("save")
    public void saveUserVideo(@RequestParam String userName, @RequestParam List<Integer> videoIds) {
        userVideoService.saveUserVideo(userName, videoIds);
    }
}
