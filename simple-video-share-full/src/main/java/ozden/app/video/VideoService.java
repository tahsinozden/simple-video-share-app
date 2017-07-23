package ozden.app.video;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ozden.app.common.FileService;

@Service
public class VideoService {

	@Autowired
	private FileService fileService;

	public String getRandomVideo(String videoFilesPath) {
		Optional<File> file = fileService.getRandomFileWithExtension(videoFilesPath, SupportedVideoFormat.MP4.getName());
		return file.isPresent() ? file.get().getName() : "";
	}

	public InputStream getVideoStream(String videoPath) {
		InputStream is = null;
		try {
			is = fileService.getFileStream(videoPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return is;
	}
}

