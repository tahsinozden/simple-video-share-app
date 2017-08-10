package ozden.app.video;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
public class VideoController {

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
