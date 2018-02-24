package ozden.app.video;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ozden.app.video.persistance.Video;
import ozden.app.video.persistance.VideoTag;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@RestController
public class VideoController {

    private final String VIDEO_SERVE_PATH = "/allvideos/";

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoMaskService videoMaskService;

    @Value("${multipart.location}")
    String savePath;

    @RequestMapping(value="/randomvideo")
    public VideoResponse getRandomVideo(){
        Optional<String> fileNameOptional = videoService.getRandomVideoName(savePath);
        String name = fileNameOptional.orElse("");
        String maskedName = videoMaskService.getMaskedVideoName(name);

        return new VideoResponse(maskedName);
    }

    @RequestMapping(value="/api/v2/randomvideo")
    public Video getRandomVideoObject(@RequestParam(value = "tagIds", required = false) List<String> tagIds){
        Optional<Video> videoOptional = videoService.getRandomVideoByTagIds(tagIds);
        if (!videoOptional.isPresent()) {
            return new Video();
        }

        Video video = videoOptional.get();
        String maskedVideoName = videoMaskService.getMaskedVideoName(video.getVideoName());
        video.setVideoName(maskedVideoName);
        video.setVideoFilePath(VIDEO_SERVE_PATH + maskedVideoName);

        return video;
    }

    @RequestMapping(value = "/allvideos/{fileName}", method = RequestMethod.GET)
    public void serveVideo(
            @PathVariable("fileName") String fileName,
            HttpServletResponse response) {

            try {
                buildResponseVideoStream(response, fileName);

            } catch (IOException e) {
                throw new RuntimeException("IOError writing file to output stream" + e);
            }
    }

    @RequestMapping(value = "/data/videotags")
    public List<VideoTag> getAllVideoTags(@RequestParam(value = "ids", required = false) List<String> ids) {
        if (!isEmpty(ids)) {
            List<Integer> intIds = ids.stream()
                    .filter(item -> !item.equals(""))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
            return videoService.getVideoTagsById(intIds);
        }
        return videoService.getAllVideoTags();
    }

    // TODO: don't return whole video info, return bean or just names
    @GetMapping("data/video")
    public List<Video> getVideoByIds(@RequestParam(value = "videoIds", required = true) List<Integer> videoIds) {
        return videoService.getVideoByIds(videoIds);
    }

    private void buildResponseVideoStream(HttpServletResponse response, String fileName) throws IOException {
        Optional<String> videoOptional = videoMaskService.getRealVideoName(fileName);
        if (!videoOptional.isPresent()) {
            return;
        }

        String realFilePath = Paths.get(savePath, videoOptional.get()).toString();
        InputStream is = videoService.getVideoStream(realFilePath);

        response.setContentType("video/mp4");
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }

    class VideoResponse {
        public String name;
        public String url;
        public VideoResponse(String name) {
            this.name = name;
            this.url = "/allvideos/" + name;
        }

    }
}
