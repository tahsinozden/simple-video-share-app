package ozden.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//@RestController
@CrossOrigin(origins = "http://localhost:8081")
@Controller
public class VideoUploader {
	
	@Value("${multipart.location}")
	String savePath;
	
	@RequestMapping(value="/uploader", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public String uploader(@RequestPart MultipartFile file /*, @RequestParam String textline*/) throws IllegalStateException, IOException{
//	public String uploader(@RequestBody RequestData reqBody) throws IllegalStateException, IOException{
//		MultipartFile file = reqBody.file;
//		savePath = "static\\videos\\";
		File videoFile = new File(savePath + "file_" + System.currentTimeMillis() + "_" + file.getOriginalFilename());
		file.transferTo(videoFile);
//		return savePath;
		return "redirect:index.html";
	}
	
	class RequestData {
		public MultipartFile file;
	}
}
