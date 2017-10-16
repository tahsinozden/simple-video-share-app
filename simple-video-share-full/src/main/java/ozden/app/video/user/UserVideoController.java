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

//    @GetMapping
//    public String hello(HttpSession httpSession) {
//        String message = "nayirrrr";
//        AuthToken token = (AuthToken)httpSession.getAttribute(TOKEN_NAME);
//        if (token != null) {
//            message ="Hello there";
//        }
//        return message;
//    }

    @PostMapping("save")
    public void saveUserVideo(@RequestParam String userName, @RequestParam List<Integer> videoIds) {
        userVideoService.saveUserVideo(userName, videoIds);
    }
}
