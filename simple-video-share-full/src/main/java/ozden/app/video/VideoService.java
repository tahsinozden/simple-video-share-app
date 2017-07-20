package ozden.app.video;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import org.springframework.stereotype.Service;


@Service
public class VideoService {

	public String getRandonVideo(String videoFilesPath) {
		File folder = new File(videoFilesPath);
		Random rn = new Random();
		File[] allFiles = folder.listFiles();
		ArrayList<File> supportedFiles = new ArrayList<>();
		for(File file : allFiles){
			if (file.isFile() && file.getName().toLowerCase().endsWith("mp4")){
				supportedFiles.add(file);
				System.out.println(file.getName());
			}
		}
		String name = supportedFiles.get(rn.nextInt(supportedFiles.size())).getName();
		return name;
	}

	public InputStream getVideoStream(String videoPath) {

		InputStream is = null;

		try {
			is = new FileInputStream(videoPath);
//			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return is;
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

