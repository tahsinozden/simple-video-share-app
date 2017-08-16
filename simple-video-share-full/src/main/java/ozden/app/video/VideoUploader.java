package ozden.app.video;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class VideoUploader {

    @Value("${multipart.location}")
    String savePath;

    @RequestMapping(value="/uploader", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<ResponseBean> uploader(@RequestPart MultipartFile file) throws IllegalStateException, IOException {
        File videoFile = new File(savePath + "file_" + System.currentTimeMillis() + "_" + file.getOriginalFilename());
        file.transferTo(videoFile);
        return new SuccessResponse();
    }

    class SuccessResponse extends ResponseEntity<ResponseBean> {
        public SuccessResponse() {
            super(new ResponseBean("success", HttpStatus.OK), HttpStatus.OK);
        }
    }
}
