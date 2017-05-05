package ozden.app.controller;

/**
 * Created by tahsin on 2017-05-05.
 */

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

@CrossOrigin(allowedHeaders = "http://localhost:8081")
@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Value("${multipart.location}")
    String savePath;


    @RequestMapping(value="/randomvideo")
    public VideoResponse getRandomVideo(){
        // get a random video
        String name = videoService.getRandonVideo(savePath);
        return  new VideoResponse(name, "/allvideos/" + name );
    }

    @RequestMapping(value = "/allvideos/{fileName}", method = RequestMethod.GET)
    public void serveVideo(
            @PathVariable("fileName") String fileName,
            HttpServletResponse response) {
        try {
            // add the file extention to the file name
            fileName += ".mp4";
            String realFilePath = Paths.get(savePath, fileName).toString();
            // get your file as InputStream
            InputStream is = videoService.getVideoStream(realFilePath);

            // copy it to response's OutputStream
            IOUtils.copy(is, response.getOutputStream());
            response.setContentType("video/mp4");
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream" + ex);
        }

    }

    class VideoResponse {
        public String name;
        public String url;
        public VideoResponse(String name, String url) {
            super();
            this.name = name;
            this.url = url;
        }

    }
}
