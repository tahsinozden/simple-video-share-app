package ozden.app.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ozden.app.video.persistance.Video;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class VideoUploader {

    @Value("${multipart.location}")
    String savePath;

    @Autowired
    private VideoService videoService;

    @RequestMapping(value="/uploader", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<ResponseBean> uploader(@RequestPart MultipartFile file) throws IllegalStateException, IOException {
        File videoFile = new File(savePath + "file_" + System.currentTimeMillis() + "_" + file.getOriginalFilename());
        file.transferTo(videoFile);
        return new SuccessResponse();
    }


    @RequestMapping(value="/api/v2/uploader", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<ResponseBean> uploaderWithVideoCategoryInfo(@RequestPart MultipartFile file,
                                                                      @RequestParam(name = "videoTagNames", required = false) List<String> videoTagNames) throws IllegalStateException, IOException {

        Optional<Video> videoOptional = videoService.saveVideo(file, videoTagNames);
        return videoOptional.isPresent() ? new SuccessResponse() : new FailResponse("error occurred!");
    }


    class SuccessResponse extends ResponseEntity<ResponseBean> {
        public SuccessResponse() {
            super(new ResponseBean("success", HttpStatus.OK), HttpStatus.OK);
        }
    }

    class FailResponse extends ResponseEntity<ResponseBean> {
        public FailResponse(String msg) {
            super(new ResponseBean(msg, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
